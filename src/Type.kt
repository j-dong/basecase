sealed class Type

object IntType : Type() { override fun toString(): String = "int" }
object BoolType : Type() { override fun toString(): String = "bool" }

data class ArrayType(val memberType: Type): Type() {
    override fun toString(): String = "$memberType[]"
}