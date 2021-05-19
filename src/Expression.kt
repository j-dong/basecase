private inline fun <T, R> nArgs(n: Int, args: List<T>, crossinline f: (List<T>) -> R): R {
    require(args.size == n)
    return f(args)
}

interface ExpressionFactory {
    fun build(children: List<Expression>): Expression
}

abstract class Expression(
    val type: Type,
    val cost: Long
) {
    open fun children(): List<Expression> = emptyList()
    abstract fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value
    abstract override fun toString(): String

    abstract fun rebuild(children: List<Expression>): Expression

    fun getRecursiveCalls(): List<RecursiveExpr> {
        if (this is RecursiveExpr) return listOf(this);
        if (children().isEmpty()) return emptyList();
        val ret = mutableListOf<RecursiveExpr>()
        for (child in children()) {
            ret.addAll(child.getRecursiveCalls())
        }
        return ret
    }
}

class Constant(val value: Value, cost: Long): Expression(value.type, cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value = value
    override fun rebuild(children: List<Expression>): Expression = nArgs(0, children) {
        Constant(value, cost)
    }
    override fun toString(): String = value.toString()
}

class Variable(val index: Int, val name: String, type: Type): Expression(type, 0) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value = dataset.getVariable(exampleIndex, index)

    override fun rebuild(children: List<Expression>): Expression = nArgs(0, children) {
        Variable(index, name, type)
    }

    override fun toString(): String = name
}

class Placeholder(val index: Int, cost: Long, val frozen: Boolean, type: Type): Expression(type, cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value = database!!.evaluatePlaceholder(this, dataset, exampleIndex)

    override fun rebuild(children: List<Expression>): Expression = nArgs(0, children) {
        Placeholder(index, cost, frozen, type)
    }

    override fun toString(): String = "{$index}"
}

enum class BinaryOperator(val symbol: String) {
    PLUS("+"), MINUS("-"), TIMES("*"), DIV("/");

    override fun toString(): String = symbol

    fun getProduction(cost: Long): Production = Production(BinaryFactory(this, cost), listOf(IntType, IntType))
}

private inline fun evalIntInt(dataset: Dataset, database: Database?, exampleIndex: Int, lhs: Expression, rhs: Expression, f: (Value, Value) -> Value): Value {
    val lhsV = lhs.evaluate(dataset, database, exampleIndex)
    val rhsV = rhs.evaluate(dataset, database, exampleIndex)
    if (lhsV is ErrorValue) {
        require(rhsV is IntValue || rhsV is ErrorValue || rhsV is InfiniteValue)
        require(lhsV.type == IntType && rhsV.type == IntType)
        return lhsV
    } else if (rhsV is ErrorValue) {
        require(lhsV is IntValue || lhsV is InfiniteValue)
        require(lhsV.type == IntType && rhsV.type == IntType)
        return rhsV
    }
    require((lhsV is IntValue || lhsV is InfiniteValue) && (rhsV is IntValue || rhsV is InfiniteValue))
    return f(lhsV, rhsV)
}

private class BinaryFactory(val op: BinaryOperator, val cost: Long): ExpressionFactory {
    override fun build(children: List<Expression>): Expression = nArgs(2, children) { childrenI ->
        BinaryExpr(op, cost, childrenI[0], childrenI[1])
    }
}

class BinaryExpr(val op: BinaryOperator, val opCost: Long, val lhs: Expression, val rhs: Expression):
        Expression(IntType, opCost + lhs.cost + rhs.cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value = evalIntInt(dataset, database, exampleIndex, lhs, rhs) { lhsV, rhsV ->
        if (lhsV is InfiniteValue) {
            if (rhsV is InfiniteValue) {
                if (op == BinaryOperator.DIV) return ErrorValue(IntType)
                val match = when (op) {
                    BinaryOperator.PLUS, BinaryOperator.TIMES, BinaryOperator.DIV -> lhsV.positive == rhsV.positive
                    BinaryOperator.MINUS -> lhsV.positive != rhsV.positive
                }
                return if (match) lhsV else ErrorValue(IntType)
            }
            require(rhsV is IntValue)
            return when (op) {
                BinaryOperator.PLUS -> lhsV
                BinaryOperator.MINUS -> lhsV
                BinaryOperator.TIMES, BinaryOperator.DIV -> when {
                    rhsV.value > 0 -> lhsV
                    rhsV.value < 0 -> InfiniteValue(!lhsV.positive)
                    else -> ErrorValue(IntType)
                }
            }
        } else if (rhsV is InfiniteValue) {
            require(lhsV is IntValue)
            return when (op) {
                BinaryOperator.PLUS -> rhsV
                BinaryOperator.MINUS -> InfiniteValue(!rhsV.positive)
                BinaryOperator.TIMES -> when {
                    lhsV.value > 0 -> rhsV
                    lhsV.value < 0 -> InfiniteValue(!rhsV.positive)
                    else -> ErrorValue(IntType)
                }
                BinaryOperator.DIV -> IntValue(0)
            }
        }
        require(lhsV is IntValue && rhsV is IntValue)
        if (op == BinaryOperator.DIV && rhsV.value == 0) {
            // divide by zero
            return ErrorValue(IntType)
        }
        return IntValue(when (op) {
            BinaryOperator.PLUS -> lhsV.value + rhsV.value
            BinaryOperator.MINUS -> lhsV.value - rhsV.value
            BinaryOperator.TIMES -> lhsV.value * rhsV.value
            BinaryOperator.DIV -> lhsV.value / rhsV.value
        })
    }

    override fun children(): List<Expression> = listOf(lhs, rhs)

    override fun rebuild(children: List<Expression>): Expression = BinaryFactory(op, opCost).build(children)

    override fun toString(): String = "($lhs $op $rhs)"
}

class RecursiveExpr(val name: String, type: Type, cost: Long, val args: List<Expression>): Expression(type, cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value {
        val inputs = args.map { arg -> arg.evaluate(dataset, database, exampleIndex) }
        val resultIndex = dataset.getExampleIndexForInputs(inputs)
        if (resultIndex == exampleIndex || resultIndex < 0) return ErrorValue(dataset.outputType)
        return dataset.getOutputForExample(resultIndex)
    }

    override fun children(): List<Expression> = args

    override fun rebuild(children: List<Expression>): Expression {
        require(children.size == args.size)
        return RecursiveExpr(name, type, cost, children)
    }

    override fun toString(): String = "$name(${args.joinToString()})"
}

class IndexFactory(val cost: Long): ExpressionFactory {
    override fun build(children: List<Expression>): Expression = nArgs(2, children) { (array, index) ->
        require(array.type is ArrayType)
        require(index.type is IntType)
        IndexExpr(array, index, cost)
    }
}

class IndexExpr(val array: Expression, val index: Expression, val indexCost: Long):
        Expression((array.type as ArrayType).memberType, indexCost + array.cost + index.cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value {
        val arrayV = array.evaluate(dataset, database, exampleIndex)
        val indexV = index.evaluate(dataset, database, exampleIndex)
        if (arrayV is ErrorValue || indexV is ErrorValue || indexV is InfiniteValue) {
            return ErrorValue(type)
        }
        require(arrayV is ArrayValue && indexV is IntValue)
        if (indexV.value < 0 || indexV.value >= arrayV.value.size) {
            return ErrorValue(type)
        }
        return arrayV.value[indexV.value]
    }

    override fun children(): List<Expression> = listOf(array, index)

    override fun rebuild(children: List<Expression>): Expression = IndexFactory(indexCost).build(children)

    override fun toString(): String = "$array[$index]"
}

enum class CompareOperator(val symbol: String) {
    EQUALS("=="), NOT_EQUALS("!="),
    LESS_THAN("<"), GREATER_THAN(">"),
    LESS_EQUALS("<="), GREATER_EQUALS(">=");

    override fun toString(): String = symbol

    fun getProduction(cost: Long): Production = Production(CompareFactory(this, cost), listOf(IntType, IntType))
    fun <T: Comparable<T>> compare(x: T, y: T): Boolean {
        return when (this) {
            EQUALS -> x == y
            NOT_EQUALS -> x != y
            LESS_THAN -> x < y
            GREATER_THAN -> x > y
            LESS_EQUALS -> x <= y
            GREATER_EQUALS -> x >= y
        }
    }
}

private class CompareFactory(val op: CompareOperator, val cost: Long): ExpressionFactory {
    override fun build(children: List<Expression>): Expression = nArgs(2, children) { childrenI ->
        CompareExpr(op, cost, childrenI[0], childrenI[1])
    }
}

class CompareExpr(val op: CompareOperator, val opCost: Long, val lhs: Expression, val rhs: Expression):
        Expression(BoolType, opCost + lhs.cost + rhs.cost) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value = evalIntInt(dataset, database, exampleIndex, lhs, rhs) { lhsV, rhsV ->
        if (lhsV is InfiniteValue) {
            if (rhsV is InfiniteValue) {
                return BoolValue(op.compare(lhsV.positive, rhsV.positive))
            }
            require(rhsV is IntValue)
            return BoolValue(when (op) {
                CompareOperator.EQUALS -> false
                CompareOperator.NOT_EQUALS -> true
                CompareOperator.GREATER_EQUALS, CompareOperator.GREATER_THAN -> lhsV.positive
                CompareOperator.LESS_EQUALS, CompareOperator.LESS_THAN -> !lhsV.positive
            })
        } else if (rhsV is InfiniteValue) {
            require(lhsV is IntValue)
            return BoolValue(when (op) {
                CompareOperator.EQUALS -> false
                CompareOperator.NOT_EQUALS -> true
                CompareOperator.GREATER_EQUALS, CompareOperator.GREATER_THAN -> !rhsV.positive
                CompareOperator.LESS_EQUALS, CompareOperator.LESS_THAN -> rhsV.positive
            })
        }
        require(lhsV is IntValue && rhsV is IntValue)
        return BoolValue(op.compare(lhsV.value, rhsV.value))
    }

    override fun children(): List<Expression> = listOf(lhs, rhs)

    override fun rebuild(children: List<Expression>): Expression = CompareFactory(op, opCost).build(children)

    override fun toString(): String = "($lhs $op $rhs)"
}

class NegExpr(val expr: Expression): Expression(BoolType, COST + expr.cost) {
    companion object {
        const val COST: Long = 1L
    }

    init {
        require(expr.type == BoolType)
    }

    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value {
        val sub = expr.evaluate(dataset, database, exampleIndex)
        if (sub is ErrorValue) return sub
        require(sub is BoolValue)
        return BoolValue(!sub.value)
    }

    override fun toString(): String = "!$expr"

    override fun children(): List<Expression> = listOf(expr)

    override fun rebuild(children: List<Expression>): Expression {
        require(children.size == 1)
        return NegExpr(children[0])
    }
}

class FuncCallFactory(val funcName: String, val signature: List<Type>, val returnType: Type, val callCost: Long, val function: (List<Value>) -> Value): ExpressionFactory {
    override fun build(children: List<Expression>): Expression = FuncCallExpr(funcName, returnType, callCost, function, children)

    fun production(): Production = Production(this, signature)
}

class FuncCallExpr(val funcName: String, type: Type, val callCost: Long, val function: (List<Value>) -> Value, val args: List<Expression>):
        Expression(type, callCost + args.fold(0L) { acc, it -> acc + it.cost }) {
    override fun evaluate(dataset: Dataset, database: Database?, exampleIndex: Int): Value {
        val argValues = args.map { it.evaluate(dataset, database, exampleIndex) }
        if (argValues.any { it is ErrorValue }) {
            return ErrorValue(type)
        }
        return function(argValues)
    }

    override fun children(): List<Expression> = args

    override fun toString(): String = "$funcName(${args.joinToString()})"

    override fun rebuild(children: List<Expression>): Expression = FuncCallExpr(funcName, type, callCost, function, children)
}