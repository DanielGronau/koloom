package koloom.model

import java.io.PrintWriter

data class KFile(
    val fileName: String,
    val kPackage: KPackage,
    val imports: List<KImport> = emptyList(),
    val members: List<KFileMember> = emptyList(),
    val annotations: List<KAnnotation> = emptyList(),
) : KElement {

    override fun writeTo(writer: PrintWriter, indent: Int) {
        with(writer) {
            kPackage.writeTo(this, indent)
            kPackage.path.ifNotEmpty { appendLine() }
            imports.forEach { it.writeTo(this, indent) }
            imports.ifNotEmpty { appendLine() }
            members.forEach { it.writeTo(this, indent) }
        }
    }

    override fun toString(): String = "<$fileName>\n${writeToString()}"

}

interface KFileMember: KElement