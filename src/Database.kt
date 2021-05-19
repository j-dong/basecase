import java.util.*
import java.util.logging.Logger
import kotlin.system.exitProcess

private class HashedExpression(val dataset: Dataset, val evalDataset: Dataset, val database: Database, val expr: Expression) {
    val cachedHash = dataset.evaluate(database, evalDataset, expr).hashCode()
    override fun hashCode(): Int = cachedHash
    override fun equals(other: Any?): Boolean {
        if (other !is HashedExpression) return false
        if (cachedHash != other.cachedHash) return false
        return dataset.evaluate(database, evalDataset, expr) == dataset.evaluate(database, evalDataset, other.expr)
    }

    override fun toString(): String = "$expr -> ${dataset.evaluate(database, evalDataset, expr)}"
}

private class WaitEntry(val expr: Expression, var numWaiting: Int)

private typealias WaitQueue = MutableList<WaitEntry>

class Database(val grammar: Grammar) {
    // NOTE: presence of a type in this map is used to indicate that it is constructable.
    private val values: MutableMap<Type, MutableList<Expression>> = mutableMapOf()
    private val valueSet: MutableSet<HashedExpression> = HashSet()
    private val valueWaiting: MutableMap<Type, WaitQueue> = mutableMapOf()
    private val queue: PriorityQueue<Expression> = PriorityQueue(Comparator.comparingLong(Expression::cost))
    private var curCost: Long = 0

    init {
        queue.add(Constant(BoolValue(true), 0))
        queue.add(Constant(BoolValue(false), 0))
        queue.add(Constant(IntValue(0), 0))
        values[BoolType] = mutableListOf()
        values[IntType] = mutableListOf()
    }

    fun enqueue(expr: Expression) {
        // mark type as constructable
        values.computeIfAbsent(expr.type) { mutableListOf() }
        queue.add(expr)
    }

    fun hasNext(): Boolean = queue.isNotEmpty()

    fun next(dataset: Dataset, evalDataset: Dataset = dataset): Expression? {
        var cur: Expression? = null
        var expr: Expression? = null
        while (queue.isNotEmpty()) {
            cur = queue.remove()!!
            mutate(cur)
            val hashed = register(dataset, evalDataset, cur) ?: continue
            for (wait in getWaiting(hashed.expr.type)) {
                wait.numWaiting -= 1
                assert(wait.numWaiting >= 0)
                if (wait.numWaiting == 0) {
                    queue.add(fixPlaceholderCosts(wait.expr))
                }
            }
            getWaiting(hashed.expr.type).clear()
            expr = hashed.expr
            break
        }
        if (cur == null || expr == null) return null
        extend(cur)
        return expr
    }

    fun evaluatePlaceholder(expr: Placeholder, dataset: Dataset, exampleIndex: Int): Value =
            values[expr.type]!![expr.index].evaluate(dataset, this, exampleIndex)

    private fun getWaiting(type: Type): WaitQueue {
        valueWaiting.computeIfAbsent(type) { mutableListOf() }
        return valueWaiting[type]!!
    }

    // returns non-null if an equivalent node has not already been visited
    private fun register(dataset: Dataset, evalDataset: Dataset, node: Expression): HashedExpression? {
        assert(node.cost >= curCost) { "cost has decreased from $curCost to ${node.cost} ($node)" }
        values.computeIfAbsent(node.type) { mutableListOf() }
        curCost = node.cost
        val frozen = HashedExpression(dataset, evalDataset, this, freezeAndReplacePlaceholders(node))
        return if (valueSet.add(frozen)) {
            values[node.type]!!.add(frozen.expr)
            frozen
        } else {
            null
        }
    }

    private fun freezeAndReplacePlaceholders(expr: Expression): Expression {
        if (expr is Placeholder) {
            assert(expr.index < values[expr.type]!!.size) { "encountered expression containing placeholder past current iteration" }
            return values[expr.type]!![expr.index]
        }
        val fixedChildren = mutableListOf<Expression>()
        for (child in expr.children()) {
            fixedChildren.add(freezeAndReplacePlaceholders(child))
        }
        return expr.rebuild(fixedChildren)
    }

    private fun fixPlaceholderCosts(expr: Expression): Expression? {
        if (expr is Placeholder) {
            // either the placeholder was initially valid (cost >= 0) or it is for the most recent value
            assert((expr.index >= values[expr.type]!!.size - 1) xor (expr.cost >= 0))
            if (expr.cost >= 0) return null
            assert(expr.index == values[expr.type]!!.size - 1)
            val realCost = values[expr.type]!![expr.index].cost
            assert(realCost >= 0)
            return Placeholder(expr.index, realCost, expr.frozen, expr.type)
        }
        val fixedChildren = mutableListOf<Expression>()
        var any = false
        for (child in expr.children()) {
            val result = fixPlaceholderCosts(child)
            fixedChildren.add(result ?: child)
            if (result != null) any = true
        }
        return if (any) {
            expr.rebuild(fixedChildren)
        } else {
            null
        }
    }

    private fun branch(expr: Expression, hasIncreasedLeft: Boolean): List<Triple<Expression, Boolean, List<WaitQueue>>> {
        if (expr is Placeholder && !expr.frozen) {
            return if (hasIncreasedLeft) {
                listOf(Triple(expr, true, emptyList()))
            } else {
                val next = expr.index + 1
                val (waitOn, placeholderCost) = if (next >= values[expr.type]!!.size) {
                    Pair(listOf(getWaiting(expr.type)), -1L)
                } else {
                    Pair(emptyList(), values[expr.type]!![next].cost)
                }
                listOf(
                        Triple(Placeholder(expr.index, expr.cost, true, expr.type), false, emptyList()),
                        Triple(Placeholder(next, placeholderCost, false, expr.type), true, waitOn)
                )
            }
        } else {
            var children = listOf<Triple<List<Expression>, Boolean, List<WaitQueue>>>(Triple(listOf(), hasIncreasedLeft, emptyList()))
            for (child in expr.children()) {
                children = children.flatMap { (children, incLeft, waitLeft) ->
                    // TODO: memoize recursion?
                    branch(child, incLeft).map { (out, incRight, waitRight) ->
                        val temp = children.toMutableList()
                        temp.add(out)
                        Triple(temp, incRight, waitLeft + waitRight)
                    }
                }
            }
            return children.map { (children, inc, waitOn) ->
                Triple(expr.rebuild(children), inc, waitOn)
            }
        }
    }

    private fun mutate(expr: Expression) {
        // special case: for integers, dynamically generate increasing/decreasing values
        if (expr is Constant && expr.value is IntValue) {
            val x = expr.value.value
            when {
                x == 0 -> {
                    queue.add(Constant(IntValue(1), 1))
                    queue.add(Constant(IntValue(-1), 1))
                }
                x > 0 -> {
                    queue.add(Constant(IntValue(x + 1), x.toLong() + 1))
                }
                x < 0 -> {
                    queue.add(Constant(IntValue(x - 1), -x.toLong() + 1))
                }
            }
        } else {
            // branch downwards
            for ((desc, inc, waitOn) in branch(expr, false)) {
                // if inc is false, it's the same as expr, so don't add it again
                if (inc) {
                    if (waitOn.isEmpty()) {
                        queue.add(desc)
                    } else {
                        val entry = WaitEntry(desc, waitOn.size)
                        for (wait in waitOn) {
                            wait.add(entry)
                        }
                    }
                }
            }
        }
    }

    private fun freeze(expr: Expression): Expression {
        if (expr is Placeholder) {
            return Placeholder(expr.index, expr.cost, true, expr.type)
        }
        return expr.rebuild(expr.children().map(this::freeze))
    }

    private fun extend(expr: Expression) {
        for (prod in grammar.getProductionsWithLeftmostType(expr.type)) {
            // freeze current and allow mutating other parameters
            val children = mutableListOf(freeze(expr))
            val waitOn = mutableListOf<WaitQueue>()
            assert(prod.signature[0] == expr.type)
            for (type in prod.signature.drop(1)) {
                val typeValues = values[type] ?: continue
                val placeholder = if (typeValues.isEmpty()) {
                    waitOn.add(getWaiting(type))
                    Placeholder(0, -1, false, type)
                } else {
                    Placeholder(0, 0, false, type)
                }
                children.add(placeholder)
            }
            val result = prod.factory.build(children)
            if (waitOn.isEmpty()) {
                queue.add(result)
            } else {
                val entry = WaitEntry(result, waitOn.size)
                for (wait in waitOn) {
                    wait.add(entry)
                }
            }
        }
        if (expr.type is BoolType && expr !is CompareExpr && expr !is NegExpr) {
            queue.add(NegExpr(expr))
        }
    }
}