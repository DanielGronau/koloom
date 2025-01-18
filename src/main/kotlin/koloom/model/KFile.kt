package koloom.model

import koloom.indent.Fragment
import koloom.indent.Indenter
import koloom.indent.Line

data class KFile(
    val fileName: String,
    val kPackage: KPackage,
    val imports: List<KImport> = emptyList(),
    val members: List<KFileMember> = emptyList(),
    val annotations: List<KAnnotation> = emptyList(),
) : KElement {

    override fun printable(): Indenter {
        val indenter = Indenter()
        indenter.add(kPackage.printable())
        kPackage.path.ifNotEmpty { indenter.add() }
        imports.forEach { indenter.add(it.printable()) }
        imports.ifNotEmpty { indenter.add() }
        members.forEach {
            when (val p = it.printable()) {
                is Fragment -> indenter.add(p.value)
                is Line -> indenter.add(p)
                is Indenter -> indenter.add(p)
            }
        }
        return indenter
    }

    override fun toString(): String = "<$fileName>\n${printable().render()}"

}

interface KFileMember : KElement {
    fun withModifiers(modifiers: List<KModifier>): KFileMember
}