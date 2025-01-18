package koloom.model

import koloom.indent.Printable

interface KElement {
    fun printable(): Printable
}