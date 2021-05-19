class Production(val factory: ExpressionFactory, val signature: List<Type>)

class Grammar private constructor(val productions: MutableList<Production>,
                                  val leftMap: MutableMap<Type, MutableList<Production>>) {
    constructor() : this(mutableListOf(), mutableMapOf())

    fun compare(op: CompareOperator, cost: Long) {
        val prod = op.getProduction(cost)
        productions.add(prod)
        leftMap.computeIfAbsent(IntType) { mutableListOf() }
        leftMap[IntType]!!.add(prod)
    }

    fun binary(op: BinaryOperator, cost: Long) {
        val prod = op.getProduction(cost)
        productions.add(prod)
        leftMap.computeIfAbsent(IntType) { mutableListOf() }
        leftMap[IntType]!!.add(prod)
    }

    fun index(base: Type, levels: Int, cost: Long) {
        var type = base
        for (i in 1..levels) {
            type = ArrayType(type)
            val prod = Production(IndexFactory(cost), listOf(type, IntType))
            productions.add(prod)
            leftMap.computeIfAbsent(type) { mutableListOf() }
            leftMap[type]!!.add(prod)
        }
    }

    fun addProduction(production: Production) {
        require(production.signature.isNotEmpty())
        val left = production.signature[0]
        productions.add(production)
        leftMap.computeIfAbsent(left) { mutableListOf() }
        leftMap[left]!!.add(production)
    }

    fun getProductionsWithLeftmostType(type: Type): List<Production> = leftMap[type] ?: emptyList()

    fun clone(): Grammar = Grammar(productions.toMutableList(), leftMap.toMutableMap())
}
