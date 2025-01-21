package koloom.model

import koloom.indent.Indenter
import koloom.indent.Line
import koloom.indent.Printable

enum class KFieldKind {
    KVAL, KVAR
}

data class KField(
    val kind: KFieldKind,
    val name: String,
    val fieldType: KFieldTypeClass,
    val defaultValue: KExpression? = null,
    val getter: KGetter? = null,
    val setter: KSetter? = null,
    val modifiers: List<KModifier> = emptyList(),
    val annotations: List<KAnnotation> = emptyList(),
) : KElement, KFileMember {
    override fun printable(): Indenter {
        val line = Line("")
        modifiers.forEach { line.add(it.printable()) }
        when (kind) {
            KFieldKind.KVAL -> line.add("val ")
            KFieldKind.KVAR -> line.add("var ")
        }
        line.add("$name: ")
        line.add(fieldType.printable())
        defaultValue?.also {
            line.add(" = ")
            line.add(defaultValue.printable())
        }
        return Indenter(lines = mutableListOf(line))
        // SETTER
        // GETTER
    }

    override fun withModifiers(modifiers: List<KModifier>): KField =copy(modifiers = modifiers)
}

class KGetter

class KSetter
