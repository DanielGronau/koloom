package koloom.model

import java.io.PrintWriter

enum class KFieldKind {
    KVAL, KVAR
}

data class KField(
    val kind: KFieldKind,
    val name: String,
    val fieldType: KFieldType,
    val defaultValue: KExpression? = null,
    val getter: KGetter? = null,
    val setter: KSetter? = null,
    val modifiers: List<KModifier> = emptyList(),
    val annotations: List<KAnnotation> = emptyList(),
) : KElement, KFileMember {
    override fun writeTo(writer: PrintWriter, indent: Int) {
        with(writer) {
            indent(indent)
            modifiers.ifNotEmpty {
                write(it.joinToString(" ", "", " ") {
                    it.toString().lowercase()
                })
            }
            when (kind) {
                KFieldKind.KVAL -> append("val ")
                KFieldKind.KVAR -> append("var ")
            }
            write("$name: ")
            fieldType.writeTo(this)
            defaultValue?.also {
                write(" = ")
                write("<not yet implemented>")
            }
            appendLine()
            // SETTER
            // GETTER
        }
    }
}

class KExpression
class KGetter
class KSetter
