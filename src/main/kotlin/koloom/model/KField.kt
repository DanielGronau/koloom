package koloom.model

import koloom.indent.Indenter
import koloom.indent.Line

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
    override fun writeTo(indenter: Indenter) {
        val line = Line("")
        modifiers.ifNotEmpty {
            line.add(it.joinToString(" ", "", " ") {
                it.toString().lowercase()
            })
        }
        when (kind) {
            KFieldKind.KVAL -> line.add("val ")
            KFieldKind.KVAR -> line.add("var ")
        }
        line.add("$name: ")
        line.add(fieldType.render())
        defaultValue?.also {
            line.add(" = ")
            line.add("<not yet implemented>")
        }
        indenter.add(line)
        // SETTER
        // GETTER
    }
}

class KExpression
class KGetter
class KSetter
