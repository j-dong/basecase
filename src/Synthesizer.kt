import java.util.logging.Logger
import kotlin.system.exitProcess

// 10 minutes
const val IMPROVEMENT_TIMEOUT: Long = 1000000000L * 60 * 10

class Synthesizer(val correctnessFactor: Double, private val logger: Logger?) {
    class Branch(val expr: Expression, val cond: List<Expression>) {
        override fun toString(): String {
            return "${cond.joinToString(" && ")} -> $expr"
        }
    }
    class Result(val branches: List<Branch>) {
        override fun toString(): String {
            return branches.joinToString("\n")
        }
    }

    fun synthesize(dataset: Dataset, seeds: List<Expression>, evalDataset: Dataset = dataset, allowErrors: Boolean = true, skipUnless: ((Expression) -> Boolean)? = null): Expression {
        val database = dataset.makeDatabase()
        for (expr in seeds) {
            database.enqueue(expr)
        }
        var bestLoss = Double.POSITIVE_INFINITY
        var bestSatisfied = 0
        var best: Expression? = null
        var iters = 0L
        var lastImprovement = System.nanoTime()
        while (true) {
            if (!database.hasNext()) {
                break
            }
            val expr = database.next(dataset, evalDataset) ?: break
            if (expr.cost > bestLoss) {
                // cannot possibly improve beyond bestLoss from now on
                logger?.info("terminating due to expression with cost ${expr.cost} (which is $expr)")
                break
            }
            if (System.nanoTime() - lastImprovement > IMPROVEMENT_TIMEOUT) {
                logger?.info("terminating due to timeout with no improvement; was exploring cost ${expr.cost} (which is $expr)")
                break
            }
            iters++
            if (skipUnless != null && !skipUnless(expr)) {
                continue
            }
            val numSatisfied = dataset.satisfied(evalDataset, null, allowErrors, expr)
            if (numSatisfied == 0) continue
            val loss = if (correctnessFactor > 0.0) {
                expr.cost.toDouble() + (dataset.numExamples - numSatisfied).toDouble() / dataset.numExamples * correctnessFactor
            } else {
                expr.cost.toDouble() + (dataset.numExamples - numSatisfied).toDouble()
            }
            if (loss <= bestLoss && numSatisfied > bestSatisfied) {
                logger?.info("improvement: $expr    ($bestLoss -> $loss = ${expr.cost} (cost) + ${dataset.numExamples - numSatisfied} (unsatisfied))")
                bestLoss = loss
                bestSatisfied = numSatisfied
                best = expr
                lastImprovement = System.nanoTime()
            }
        }
        if (best == null) throw IllegalStateException("could not find expression")
        logger?.info("finished after $iters iteration(s)")
        logger?.info("best expression: $best with loss $bestLoss")
        return best
    }

    fun synthesizeCondition(boolDataset: Dataset, exprDataset: Dataset, seeds: List<Expression>): List<Expression> {
        require(boolDataset.outputType == BoolType)
        if (boolDataset.isTrivial()) return listOf(Constant(BoolValue(true), 0))
        var dataset = boolDataset
        val ret = mutableListOf<Expression>()
        while (true) {
            val expr = synthesize(dataset, seeds, exprDataset, false) { expr ->
                if (expr is Constant) {
                    false
                } else {
                    dataset.validFilter(exprDataset, expr)
                }
            }
            ret.add(expr)
            dataset = dataset.restrict(exprDataset, expr) ?: break
        }
        return ret
    }

    fun synthesizeProgram(dataset: Dataset, boolGrammar: Grammar, seeds: List<Expression>): Result {
        val startTime = System.nanoTime()
        val branches = mutableListOf<Branch>()
        var curDataset = dataset
        while (true) {
            logger?.info("SYNTHESIZING expression: ${curDataset.numExamples} example(s) left")
            val expr = synthesize(curDataset, seeds, dataset)
            logger?.info("SYNTHESIZING condition for $expr")
            val cond = synthesizeCondition(curDataset.toBool(dataset, expr, boolGrammar), dataset, seeds)
            branches.add(Branch(expr, cond))
            val oldNum = curDataset.numExamples
            curDataset = curDataset.removeIf(dataset, cond) ?: break
            // assert(curDataset.numExamples < oldNum)
            if (curDataset.numExamples >= oldNum) {
                curDataset.removeIf(dataset, cond)
                Logger.getGlobal().severe("failed to remove any examples")
                exitProcess(1)
            }
            logger?.info("removed ${oldNum - curDataset.numExamples} example(s)")
        }
        val endTime = System.nanoTime()
        logger?.info("synthesized program in ${(endTime - startTime).toDouble() * 1.0e-9}s")
        return Result(branches)
    }

    data class MissingCases(val cases: List<List<Value>>): Exception()

    companion object {
        private fun Value.isTrue(): Boolean {
            require(this is BoolValue)
            return this.value
        }
        private fun evaluateExpression(expr: Expression, dataset: Dataset, index: Int): Value {
            val calls = expr.getRecursiveCalls().map { call ->
                call.args.map { evaluateExpression(it, dataset, index) }
            }.filter { args ->
                !dataset.containsInputs(args) || dataset.getOutputForExample(dataset.getExampleIndexForInputs(args)) is ErrorValue
            }
            if (calls.isNotEmpty()) {
                throw MissingCases(calls)
            }
            val ret = expr.evaluate(dataset, null, index)
            assert(ret !is ErrorValue) { "got error when evaluating expression $expr on example ${ dataset.getExample(index) }" }
            return ret
        }
        fun evaluateProgram(program: Result, dataset: Dataset, index: Int): Value {
            for (branch in program.branches) {
                if (branch.cond.all { expr -> evaluateExpression(expr, dataset, index).isTrue() }) {
                    return evaluateExpression(branch.expr, dataset, index)
                }
            }
            throw RuntimeException("case not covered when evaluating program")
        }
    }

    fun synthesizeTestCase(testCase: TestCase): Result {
        val dataset = Dataset(testCase.parameterTypes, testCase.parameterNames, testCase.outputType, testCase.exprGrammar)
        val queue: MutableList<List<Value>> = testCase.inputs.toMutableList()
        val indeg: MutableMap<List<Value>, Int> = mutableMapOf()
        val waiting: MutableMap<List<Value>, MutableList<List<Value>>> = mutableMapOf()
        while (queue.isNotEmpty()) {
            val cur = queue.removeAt(queue.size - 1)
            if (!dataset.containsInputs(cur)) {
                dataset.add(cur, ErrorValue(testCase.outputType))
            }
            logger?.finer("trying to evaluate at $cur")
            try {
                dataset.setOutput(cur, evaluateProgram(testCase.spec, dataset, dataset.getExampleIndexForInputs(cur)))
                logger?.finer("worked!")
                for (unlock in waiting[cur] ?: continue) {
                    logger?.finer("unlocking $unlock")
                    indeg[unlock] = indeg[unlock]!! - 1
                    if (indeg[unlock] == 0) {
                        logger?.finer("in-degree set to zero!")
                        queue.add(unlock)
                    }
                }
                waiting.remove(cur)
            } catch (e : MissingCases) {
                assert(!indeg.containsKey(cur) || indeg[cur] == 0)
                logger?.finer("missing cases: ${e.cases}")
                indeg[cur] = e.cases.size
                for (wait in e.cases) {
                    if (!waiting.containsKey(wait)) {
                        waiting[wait] = mutableListOf()
                    }
                    waiting[wait]!!.add(cur)
                    if (!dataset.containsInputs(wait)) {
                        queue.add(wait)
                    }
                }
            }
        }
        if (waiting.isNotEmpty()) {
            throw RuntimeException("infinite recursion: ${waiting.keys}")
        }
        logger?.info("generated ${dataset.numExamples} examples in total")
        logger?.info("proceeding to synthesis")
        return synthesizeProgram(dataset, testCase.boolGrammar, testCase.seeds)
    }
}