package koloom.model

import java.io.PrintWriter

const val BULK_IMPORT = "_"

data class KImport(
    val path: String,
    val ref: String = BULK_IMPORT,
    val alias: String? = null
) : KElement {
    override fun writeTo(writer: PrintWriter, indent: Int) {
        with(writer) {
            indent(indent)
            append("import ")
            path.ifNotEmpty { append("$path.") }
            append(ref)
            alias.ifNotEmpty { append(" as $alias") }
            appendLine()
        }
    }

    override fun toString(): String = writeToString()
}


