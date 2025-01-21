package koloom.model

import koloom.indent.Line
import kotlin.reflect.KClass

const val BULK_IMPORT = "_"

data class KImport(
    val path: String,
    val ref: String = BULK_IMPORT,
    val alias: String? = null
) : KElement {

    override fun printable(): Line {
        val line = Line("import ")
        path.ifNotEmpty { line.add("$path.") }
        line.add(ref)
        alias.ifNotEmpty { line.add(" as $alias") }
        return line
    }

    fun isBulkImport() = ref == BULK_IMPORT

    override fun toString(): String = printable().value
}

fun KClass<*>.toImport() = KImport(qualifiedName!!.initOfPath(), simpleName!!)

