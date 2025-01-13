package koloom.model

import koloom.indent.Indenter
import koloom.indent.Line
import kotlin.reflect.KClass

sealed interface KFieldType : KElement {
    fun imports(): List<KImport>
}

data class KFieldTypeClass(val path: String, val name: String, val typeParameters: List<KFieldType> = emptyList()) :
    KFieldType {
    override fun writeTo(indenter: Indenter) {
        val line = Line(name)
        typeParameters.ifNotEmpty {
            line.add(it.joinToString(", ", "<", ">") { it.render() })
        }
        indenter.add(line)
    }

    override fun imports(): List<KImport> = listOf(KImport(path, name)) +
            typeParameters.flatMap { it.imports() }
}

fun KClass<*>.toType(vararg typeParameters: KFieldType) =
    KFieldTypeClass(
        qualifiedName!!.initOfPath(),
        simpleName!!,
        typeParameters.toList()
    ).also {
        if (typeParameters.size != this@toType.typeParameters.size)
            error("Wrong number of type arguments, expected ${this@toType.typeParameters.size} and got ${typeParameters.size}")
    }

data class KFieldTypeParameter(
    val name: String,
    val baseType: KFieldType? = null,
    val modifiers: List<KTypeModifier> = emptyList()
) : KFieldType {
    override fun writeTo(indenter: Indenter) {
        val line = Line("")
        line.add(modifiers.joinToString(" ", "", " "))
        line.add(name)
        if (baseType != null) {
            line.add(": ")
            line.add(baseType.render())
        }
        indenter.add(line)
    }

    override fun imports(): List<KImport> = emptyList()
}

val STAR = KFieldTypeParameter("*")

