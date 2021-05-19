import java.lang.RuntimeException
import java.util.logging.Logger

class Dataset(private val signature: List<Type>, private val parameterNames: List<String>, val outputType: Type, val grammar: Grammar) {
    data class Example(val input: List<Value>, val output: Value)

    private val examples: MutableList<Example> = mutableListOf()
    private val inputMap: MutableMap<List<Value>, Int> = HashMap()

    val numExamples
        get() = examples.size

    fun add(inputs: List<Value>, output: Value) {
        val old = inputMap.putIfAbsent(inputs, examples.size)
        if (old != null) {
            require(output == examples[old].output) { "output $output for input $inputs does not match old value $old" }
            return
        }
        examples.add(Example(inputs, output))
    }

    fun setOutput(inputs: List<Value>, output: Value) {
        examples[inputMap[inputs]!!] = Example(inputs, output)
    }

    /**
     * Gets the variable value for the given example and variable indices, or error if invalid.
     */
    fun getVariable(exampleIndex: Int, varIndex: Int): Value = examples.getOrNull(exampleIndex)?.input?.get(varIndex)
            ?: ErrorValue(signature[varIndex])

    /**
     * Gets the example index for the given list of inputs, or -1 if not present.
     */
    fun getExampleIndexForInputs(inputs: List<Value>): Int {
        require(inputs.size == signature.size)
        return inputMap[inputs] ?: -1
    }

    fun containsInputs(inputs: List<Value>): Boolean {
        require(inputs.size == signature.size)
        return inputMap.containsKey(inputs)
    }

    fun getOutputForExample(exampleIndex: Int): Value = examples[exampleIndex].output

    fun evaluate(database: Database?, evalDataset: Dataset, expr: Expression): List<Value> = (0 until examples.size).map { i -> expr.evaluate(evalDataset, database, translateExampleIndex(evalDataset, i)) }

    private fun translateExampleIndex(evalDataset: Dataset, i: Int): Int {
        if (evalDataset === this) {
            return i
        } else {
            return evalDataset.getExampleIndexForInputs(examples[i].input)
        }
    }

    fun satisfied(evalDataset: Dataset, database: Database?, allowErrors: Boolean, expr: Expression): Int {
        if (expr.type != outputType) {
            return 0
        }
        var ret = 0
        for (i in 0 until examples.size) {
            val out = expr.evaluate(evalDataset, database, translateExampleIndex(evalDataset, i))
            val expected = examples[i].output
            if (!allowErrors && out is ErrorValue) {
                return 0
            }
            if (out == expected) {
                ret += 1
            }
        }
        return ret
    }

    fun debugSatisfied(evalDataset: Dataset, database: Database?, expr: Expression) {
        if (expr.type != outputType) {
            return
        }
        for (i in 0 until examples.size) {
            val out = expr.evaluate(evalDataset, database, translateExampleIndex(evalDataset, i))
            val expected = examples[i].output
            Logger.getGlobal().info("${examples[i]} -> $out (expected: $expected)")
        }
    }

    /**
     * Converts a dataset to one whose output is whether the given expression matches this one.
     */
    fun toBool(evalDataset: Dataset, expr: Expression, boolGrammar: Grammar): Dataset {
        val ret = Dataset(signature, parameterNames, BoolType, boolGrammar)
        for (i in 0 until examples.size) {
            val example = examples[i]
            ret.add(example.input, BoolValue(example.output == expr.evaluate(evalDataset, null, translateExampleIndex(evalDataset, i))))
        }
        return ret
    }

    fun isTrivial(): Boolean = examples.all { example -> example.output == BoolValue(true) }

    /**
     * Restricts this dataset to the set of examples for which the given expression returns true.
     * Returns null if there are no remaining false positives.
     */
    fun restrict(evalDataset: Dataset, expr: Expression): Dataset? {
        if (outputType != BoolType) {
            throw IllegalStateException("can only restrict bool datasets")
        }
        val ret = Dataset(signature, parameterNames, BoolType, grammar)
        var hasFalsePositives = false
        var hasNegatives = false
        for (i in 0 until examples.size) {
            val example = examples[i]
            if (expr.evaluate(evalDataset, null, translateExampleIndex(evalDataset, i)) != BoolValue(true)) {
                hasNegatives = true
                continue
            }
            if (example.output == BoolValue(false)) {
                hasFalsePositives = true
            }
            ret.add(example.input, example.output)
        }
        if (!hasFalsePositives) return null
        if (!hasNegatives) {
            throw RuntimeException("cannot restrict by true")
        }
        return ret
    }

    /**
     * Removes the examples for which all given expressions return true.
     * Returns null if no remaining examples.
     */
    fun removeIf(evalDataset: Dataset, cond: List<Expression>): Dataset? {
        require(cond.all { expr -> expr.type == BoolType })
        val ret = Dataset(signature, parameterNames, outputType, grammar)
        for (i in 0 until examples.size) {
            val example = examples[i]
            if (cond.all { expr -> expr.evaluate(evalDataset, null, translateExampleIndex(evalDataset, i)) == BoolValue(true) }) {
                continue
            }
            ret.add(example.input, example.output)
        }
        if (ret.examples.isEmpty()) return null
        return ret
    }

    fun makeDatabase() = Database(grammar).apply {
        require(signature.size == parameterNames.size)
        signature.forEachIndexed { i, type -> this.enqueue(Variable(i, parameterNames[i], type)) }
    }

    fun validFilter(evalDataset: Dataset, expr: Expression): Boolean {
        require(outputType == BoolType)
        if (expr.type != BoolType) {
            return false
        }
        for (i in 0 until examples.size) {
            val example = examples[i]
            if (example.output != BoolValue(true)) {
                continue
            }
            if (expr.evaluate(evalDataset, null, translateExampleIndex(evalDataset, i)) == BoolValue(true)) {
                return true
            }
        }
        return false
    }

    fun getExample(index: Int): Example = examples[index]
}
