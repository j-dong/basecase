sealed class Value {
    abstract val type: Type
}

data class BoolValue(val value: Boolean): Value() {
    override val type: Type
        get() = BoolType

    override fun toString(): String = value.toString()
}
data class IntValue(val value: Int): Value() {
    override val type: Type
        get() = IntType

    override fun toString(): String = value.toString()
}
data class ArrayValue(val value: List<Value>, override val type: Type): Value() {
    override fun toString(): String = value.toString()
}
data class ErrorValue(override val type: Type): Value() {
    override fun toString(): String = "<error>"
}
data class InfiniteValue(val positive: Boolean = true): Value() {
    override val type: Type
        get() = IntType

    override fun toString(): String = if (positive) "infinity" else "-infinity"
}