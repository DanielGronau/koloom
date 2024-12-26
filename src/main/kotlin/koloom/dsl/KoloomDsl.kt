package koloom.dsl

import kotlin.annotation.AnnotationTarget.*

@DslMarker
@Target(CLASS, TYPE)
annotation class KoloomDsl
