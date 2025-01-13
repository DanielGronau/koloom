package koloom.indent

import java.io.PrintWriter

data class Line(var value: String, val indent: Int = 0) {
    fun render(): String = " ".repeat(indent) + value + "\n"

    fun writeTo(writer: PrintWriter) {
        writer.write(render())
    }

    fun add(more: String) = this.apply{ this.value = value + more }
}

data class Indenter(private val lines: MutableList<Line> = mutableListOf()) {

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

    fun render(): String =
        lines.joinToString(""){ it.render() }

    fun writeTo(writer: PrintWriter) {
        lines.forEach { it.writeTo(writer) }
    }
}


