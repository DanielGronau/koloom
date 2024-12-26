package koloom.model

import java.io.PrintWriter
import kotlin.reflect.KClass

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
            when (kind) {
                KFieldKind.KVAL -> append("val")
                KFieldKind.KVAR -> append("var")
            }
            appendLine()
            // SETTER
            // GETTER
        }
    }

    override fun imports(): List<KImport> {
        return emptyList()
    }
}

class KExpression
class KGetter
class KSetter
