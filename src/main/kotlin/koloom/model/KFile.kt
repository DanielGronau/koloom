package koloom.model

import koloom.indent.Indenter

data class KFile(
    val fileName: String,
    val kPackage: KPackage,
    val imports: List<KImport> = emptyList(),
    val members: List<KFileMember> = emptyList(),
    val annotations: List<KAnnotation> = emptyList(),
) : KElement {

    override fun writeTo(indenter: Indenter) {
        indenter.add(kPackage.render())
        kPackage.path.ifNotEmpty { indenter.add() }
        imports.forEach { indenter.add(it.render()) }
        imports.ifNotEmpty { indenter.add() }
        members.forEach { it.writeTo(indenter) }
    }

    override fun toString(): String = "<$fileName>\n${render()}"

}

interface KFileMember : KElement