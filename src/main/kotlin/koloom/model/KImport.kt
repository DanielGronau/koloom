package koloom.model

import koloom.indent.Indenter
import koloom.indent.Line

const val BULK_IMPORT = "_"

data class KImport(
    val path: String,
    val ref: String = BULK_IMPORT,
    val alias: String? = null
) : KElement {
    override fun writeTo(indenter: Indenter) {
        val line = Line("import ")
        path.ifNotEmpty { line.add("$path.") }
        line.add(ref)
        alias.ifNotEmpty { line.add(" as $alias") }
        indenter.add(line)
    }

    override fun toString(): String = render()
}


