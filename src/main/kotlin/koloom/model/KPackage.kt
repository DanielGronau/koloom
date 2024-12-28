package koloom.model

import java.io.PrintWriter

val EMPTY_PACKAGE = KPackage("")

data class KPackage(
    val path: String
) : KElement {
    override fun writeTo(writer: PrintWriter, indent: Int) {
        if (path.isNotEmpty()) with(writer) {
            indent(indent)
            append(path)
            appendLine()
        }
    }

    override fun toString(): String = writeToString()

    fun isEmpty() = path.isEmpty()
}


