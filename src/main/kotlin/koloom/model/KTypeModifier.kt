package koloom.model

import koloom.indent.Fragment

sealed interface KTypeModifier : KElement {
    override fun printable() = Fragment(toString().lowercase() + " ")
}

data object REIFIED: KTypeModifier
data object IN: KTypeModifier
data object OUT: KTypeModifier
