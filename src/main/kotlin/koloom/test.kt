package koloom

import koloom.dsl.FILE
import koloom.model.OVERRIDE
import koloom.model.PRIVATE
import koloom.model.toType

fun main() {
    val file = FILE("funny.kt", "one.two.three") {
        IMPORTS {
            +"foo.bar.Baz"
            +"java.nio._"
            ALIAS("java.lang.StringBuffer", "SB")
            +String::class
        }

        OVERRIDE()
        PRIVATE()
        VAL("foo", List::class.toType(String::class.toType()))
    }
    println(file)
}