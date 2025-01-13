package koloom.model

import koloom.indent.Indenter

val EMPTY_PACKAGE = KPackage("")

data class KPackage(
    val path: String
) : KElement {
    override fun writeTo(indenter: Indenter) {
        if (path.isNotEmpty()) {
            indenter.add(path)
        }
    }

    override fun toString(): String = render()

    fun isEmpty() = path.isEmpty()
}


