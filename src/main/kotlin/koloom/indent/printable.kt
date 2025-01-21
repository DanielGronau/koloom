package koloom.indent

import java.io.PrintWriter

interface Printable {
    fun render(): String

    fun writeTo(writer: PrintWriter) {
        writer.write(render())
    }
}

data class Fragment(var value: String): Printable {
    override fun render(): String = value

    fun add(more: String) = this.apply{ this.value = value + more }
    fun add(more: Fragment) = this.apply{ this.value = value + more.value }
}

data class Line(var value: String, val indent: Int = 0): Printable {
    override fun render(): String = " ".repeat(indent) + value + "\n"

    override fun writeTo(writer: PrintWriter) {
        writer.write(render())
    }

    fun add(more: String) = this.apply{ this.value = value + more }
    fun add(more: Fragment) = this.apply{ this.value = value + more.value }
}

data class Indenter(private val lines: MutableList<Line> = mutableListOf()): Printable {

    fun add(line: Line) = this.apply {
        lines += line
    }

    fun add(value: String = "", indent: Int = 0) = this.apply {
        lines += Line(value, indent)
    }

    fun add(indenter: Indenter, indent: Int = 0) = this.apply {
        indenter.lines.forEach {
            lines += Line(it.value, it.indent + indent)
        }
    }

    override fun render(): String =
        lines.joinToString(""){ it.render() }

    override fun writeTo(writer: PrintWriter) {
        lines.forEach { it.writeTo(writer) }
    }
}


