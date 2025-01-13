package koloom.model

import koloom.indent.Indenter

interface KElement {

    fun writeTo(indenter: Indenter)

    fun render(): String = Indenter()
        .also { writeTo(it) }
        .render()
}