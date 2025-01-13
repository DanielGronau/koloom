package koloom.model

import java.io.PrintWriter

fun String?.ifNotEmpty(body: (String) -> Unit) {
    if (this != null && !isEmpty()) {
        body(this)
    }
}

fun <T: Collection<*> > T.ifNotEmpty(body: (T) -> Unit) {
    if (!this.isEmpty()) {
        body(this)
    }
}

fun String.lastOfPath(delimiter: String = ".") =
    when {
        lastIndexOf(delimiter) == -1 -> this
        else -> substring(lastIndexOf(delimiter)+1)
    }

fun String.initOfPath(delimiter: String = ".") =
    when {
        lastIndexOf(delimiter) == -1 -> ""
        else -> substring(0,lastIndexOf(delimiter))
    }

fun PrintWriter.indent(n: Int): PrintWriter = append(" ".repeat(n))