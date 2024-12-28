package koloom.model

sealed interface KTypeModifier

data object REIFIED: KTypeModifier
data object IN: KTypeModifier
data object OUT: KTypeModifier
