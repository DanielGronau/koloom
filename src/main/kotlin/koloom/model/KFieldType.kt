package koloom.model

import java.io.PrintWriter
import kotlin.reflect.KClass

sealed interface KFieldType : KElement {
    fun imports(): List<KImport>
}

data class KFieldTypeClass(val path: String, val name: String, val typeParameters: List<KFieldType> = emptyList()): KFieldType {
    override fun writeTo(writer: PrintWriter, indent: Int) {
        with(writer) {
            write(name)
            typeParameters.ifNotEmpty {
                write(it.joinToString(", ", "<",">"){ it.writeToString() })
            }
        }
    }

    override fun imports(): List<KImport> = listOf(KImport(path, name)) +
            typeParameters.flatMap { it.imports() }
}

fun KClass<*>.toType(vararg typeParameters: KFieldType) =
    KFieldTypeClass(qualifiedName!!.initOfPath(),
        simpleName!!,
        typeParameters.toList()).also {
            if (typeParameters.size != this@toType.typeParameters.size)
                error("Wrong number of type arguments, expected ${this@toType.typeParameters.size} and got ${typeParameters.size}")
    }

data class KFieldTypeParameter(
    val name: String,
    val baseType: KFieldType? = null,
    val modifiers: List<KTypeModifier> = emptyList()
): KFieldType {
    override fun writeTo(writer: PrintWriter, indent: Int) {
        with(writer) {
            write(modifiers.joinToString(" ", "", " "))
            write(name)
            if (baseType != null) {
                write(": ")
                baseType.writeTo(writer)
            }
        }
    }

    override fun imports(): List<KImport> = emptyList()
}

val STAR = KFieldTypeParameter("*")

