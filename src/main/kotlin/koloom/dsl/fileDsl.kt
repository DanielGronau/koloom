package koloom.dsl

import koloom.model.KExpression
import koloom.model.KField
import koloom.model.KFieldKind
import koloom.model.KFieldType
import koloom.model.KFile
import koloom.model.KFileMember
import koloom.model.KImport
import koloom.model.KModifier
import koloom.model.KPackage
import koloom.model.initOfPath
import koloom.model.lastOfPath
import koloom.model.toType
import kotlin.reflect.KClass

fun FILE(fileName: String, pkg: String = "", body: FileScope.() -> Unit): KFile =
    FileScope()
        .apply { body() }
        .build(fileName, KPackage(pkg))

@KoloomDsl
class FileScope {

    object Terminal

    val importScope = ImportScope()
    val members = mutableListOf<KFileMember>()

    fun IMPORTS(body: ImportScope.() -> Unit) {
        with(importScope) { body() }
    }

    @KoloomDsl
    class ImportScope {
        var imports = mutableListOf<KImport>()

        operator fun String.unaryPlus() {
            imports += KImport(initOfPath(), lastOfPath())
        }

        operator fun KClass<*>.unaryPlus() {
            imports += KImport(qualifiedName!!.initOfPath(), simpleName!!)
        }

        fun ALIAS(path: String, alias: String) {
            imports += KImport(path.initOfPath(), path.lastOfPath(), alias)
        }
    }

    fun VAL(name: String, type: KFieldType, defaultValue: KExpression? = null): Terminal {
        members += KField(
            kind = KFieldKind.KVAL,
            name = name,
            fieldType = type,
            defaultValue = defaultValue,
            modifiers = emptyList(),
            annotations = emptyList()
        ).also {
            importScope.imports += it.fieldType.imports()
        }
        return Terminal
    }
    fun VAL(name: String, type: KClass<*>, defaultValue: KExpression? = null): Terminal =
        VAL(name, type.toType(), defaultValue)

    operator fun KModifier.rangeTo(mc: Terminal) {
        members[members.lastIndex] = members.last().withModifiers(listOf(this))
    }
    operator fun List<KModifier>.rangeTo(mc: Terminal) {
        members[members.lastIndex] = members.last().withModifiers(this)
    }
    operator fun KModifier.rangeTo(modifier: KModifier) = listOf(this, modifier)
    operator fun List<KModifier>.rangeTo(modifier: KModifier) = this + modifier

    fun build(fileName: String, pkg: KPackage): KFile =
        KFile(fileName, pkg, importScope.imports.distinct(), members = members)
}