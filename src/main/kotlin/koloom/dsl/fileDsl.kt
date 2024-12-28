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
import kotlin.reflect.KClass

fun FILE(fileName: String, pkg: String = "", body: FileScope.() -> Unit): KFile =
    FileScope()
        .apply { body() }
        .build(fileName, KPackage(pkg))

@KoloomDsl
class FileScope {

    val importScope = ImportScope()
    val members = mutableListOf<KFileMember>()
    val modifierStack = mutableListOf<KModifier>()

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

    fun VAL(name: String, type: KFieldType, defaultValue: KExpression? = null) {
        members += KField(
            kind = KFieldKind.KVAL,
            name = name,
            fieldType = type,
            defaultValue = defaultValue,
            modifiers = modifierStack.toList(),
            annotations = emptyList()
        ).also {
            modifierStack.clear()
            importScope.imports += it.fieldType.imports()
        }

    }

    operator fun KModifier.invoke() {
        modifierStack += this
    }

    fun build(fileName: String, pkg: KPackage): KFile =
        KFile(fileName, pkg, importScope.imports.distinct(), members = members)
}