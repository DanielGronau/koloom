package koloom.model

import java.io.PrintWriter
import java.io.StringWriter

interface KElement {

    fun writeTo(writer: PrintWriter, indent: Int = 0)

    fun writeToString(indent: Int = 0): String = StringWriter()
        .also { writeTo(PrintWriter(it), indent) }
        .toString()

    fun PrintWriter.indent(n: Int) = append(" ".repeat(n))
}