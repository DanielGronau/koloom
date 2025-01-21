package koloom

import koloom.dsl.FILE
import koloom.dsl.FileScope.IMPORT
import koloom.model.NULL
import koloom.model.OVERRIDE
import koloom.model.PRIVATE
import koloom.model.toNullableType
import koloom.model.toType
import java.util.*

fun main() {

    val file = FILE("funny.kt", "one.two.three") {
        IMPORT.."foo.bar.Baz"
        IMPORT.."java.nio._"
        IMPORT.."java.lang.StringBuffer" AS "SB"
        IMPORT..String::class
        IMPORT..Date::class AS "JavaDate"

        OVERRIDE..PRIVATE..VAL("foo", List::class.toNullableType(String::class.toType()), NULL)
        VAL("bar", String::class)

        /*
        PRIVATE .. VAL {
            "foo" .. String::class
        }
        VAR {
            "bar" EQ S("test")
            GET() {
                + "if(value.isEmpty()){"
                + "  error(\"what a crap!\"!)"
                + "}"
                + "value"
            }
        }
        */
    }
    println(file)
}