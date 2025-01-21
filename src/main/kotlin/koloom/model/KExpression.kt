package koloom.model

import koloom.indent.Fragment

sealed abstract class KExpression() : KElement {
    abstract val imports: List<KImport>
    abstract override fun printable(): Fragment
}

data class StringLiteral(val value: String) : KExpression() {
    override val imports = listOf(String::class.toImport())
    override fun printable(): Fragment = Fragment(""""$value"""")
}

val String.L: StringLiteral
    get() = StringLiteral(this)

abstract class ConstantExpression : KExpression() {
    override val imports: List<KImport> = emptyList()
    override fun printable(): Fragment = Fragment(toString().lowercase())
}

data object NULL : ConstantExpression()
data object TRUE : ConstantExpression()
data object FALSE : ConstantExpression()


