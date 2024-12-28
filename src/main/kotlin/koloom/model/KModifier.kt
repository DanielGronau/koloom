package koloom.model

sealed interface KModifier

data object PUBLIC : KModifier
data object PRIVATE : KModifier
data object INTERNAL : KModifier
data object PROTECTED : KModifier
data object OVERRIDE : KModifier
data object OPEN : KModifier
data object CONST : KModifier
data object ABSTRACT : KModifier
data object COMPANION : KModifier
data object ANNOTATION : KModifier
data object ENUM : KModifier
data object DATA : KModifier
data object LATEINIT : KModifier
