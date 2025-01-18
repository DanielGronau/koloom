package koloom.model

import koloom.indent.Indenter
import koloom.indent.Line

val EMPTY_PACKAGE = KPackage("")

data class KPackage(
    val path: String
) : KElement {
    override fun printable(): Indenter {
        val indenter = Indenter()
        if (path.isNotEmpty()) {
            indenter.add(Line(path))
        }
        return indenter
    }

    override fun toString(): String = printable().render()

    fun isEmpty() = path.isEmpty()
}


