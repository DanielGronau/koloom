package koloom.model

import koloom.indent.Fragment
import kotlin.reflect.KClass

sealed interface KFieldType : KElement {
    fun imports(): List<KImport>
    override fun printable(): Fragment
}

data class KFieldTypeClass(val path: String, val name: String, val typeParameters: List<KFieldType> = emptyList()) :
    KFieldType {
    override fun printable(): Fragment {
        val fragment = Fragment(name)
        typeParameters.ifNotEmpty {
            fragment.add("<")
            typeParameters.forEach { it: KFieldType ->
                fragment.add(it.printable())
            }
            fragment.add(">")
        }
        return fragment
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
    override fun printable() : Fragment {
        val fragment = Fragment("")
        modifiers.forEach { fragment.add(it.printable()) }
        fragment.add(modifiers.joinToString(" ", "", " "))
        fragment.add(name)
        if (baseType != null) {
            fragment.add(": ")
            fragment.add(baseType.printable())
        }
        return fragment
    }

    override fun imports(): List<KImport> = emptyList()
}

val STAR = KFieldTypeParameter("*")

