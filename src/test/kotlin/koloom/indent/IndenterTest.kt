package koloom.indent

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.PrintWriter
import java.io.StringWriter

class IndenterTest : StringSpec({

    "should add and render lines as expected" {
        val tested = Indenter()
            .add("abc")
            .add("def", 2)
            .add(Line("ghi", 4))

        val expected = "abc\n  def\n    ghi\n"

        tested.render() shouldBe expected
        stringFromWriter(tested) shouldBe expected
    }

    "should add and render other indenters as expected" {
        val inner = Indenter()
            .add("abc")
            .add("def", 2)
            .add(Line("ghi", 4))

        val tested = Indenter()
            .add("first")
            .add(inner, 2)
            .add("last", 2)

        val expected = "first\n  abc\n    def\n      ghi\n  last\n"
        tested.render() shouldBe expected
        stringFromWriter(tested) shouldBe expected
    }

    "works for multiple levels of indenters" {
        val inner1 = Indenter()
            .add("abc", 2)

        val inner2 = Indenter()
            .add(inner1, 3)

        val tested = Indenter()
            .add(inner2, 5)

        val expected = "          abc\n"
        tested.render() shouldBe expected
        stringFromWriter(tested) shouldBe expected
    }
})

private fun stringFromWriter(indenter: Indenter) = StringWriter()
    .also { indenter.writeTo(PrintWriter(it)) }
    .toString()
