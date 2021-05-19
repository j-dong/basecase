import kotlin.math.max
import kotlin.math.min

data class Builtin(val name: String, val signature: List<Type>, val returnType: Type, val function: (List<Value>) -> Value) {
    fun production(cost: Long): Production = FuncCallFactory(name, signature, returnType, cost, function).production()
}

private fun gather(builtins: List<Builtin>): Map<String, Builtin> {
    return builtins.associateBy { it.name }
}

val BUILTINS: Map<String, Builtin> = gather(listOf(
    Builtin("max", listOf(IntType, IntType), IntType) { (lhs, rhs) ->
        if (lhs is InfiniteValue) {
            if (lhs.positive) {
                lhs
            } else {
                rhs
            }
        } else if (rhs is InfiniteValue) {
            if (rhs.positive) {
                rhs
            } else {
                lhs
            }
        } else {
            require(lhs is IntValue && rhs is IntValue)
            IntValue(max(lhs.value, rhs.value))
        }
    },
    Builtin("min", listOf(IntType, IntType), IntType) { (lhs, rhs) ->
        if (lhs is InfiniteValue) {
            if (lhs.positive) {
                rhs
            } else {
                lhs
            }
        } else if (rhs is InfiniteValue) {
            if (rhs.positive) {
                lhs
            } else {
                rhs
            }
        } else {
            require(lhs is IntValue && rhs is IntValue)
            IntValue(min(lhs.value, rhs.value))
        }
    },
    Builtin("subarray_equals", listOf(ArrayType(IntType), ArrayType(IntType), IntType, IntType), BoolType) { (a, b, x, y) ->
        if (a is InfiniteValue || b is InfiniteValue) {
            return@Builtin ErrorValue(BoolType)
        }
        require(a is ArrayValue && b is ArrayValue)
        require(x is IntValue && y is IntValue)
        if (x.value > a.value.size || y.value > b.value.size) {
            ErrorValue(BoolType)
        } else if (x != y) {
            BoolValue(false)
        } else {
            var ret = true
            for (i in 0 until x.value) {
                if (a.value[i] != b.value[i]) {
                    ret = false
                }
            }
            BoolValue(ret)
        }
    }
))