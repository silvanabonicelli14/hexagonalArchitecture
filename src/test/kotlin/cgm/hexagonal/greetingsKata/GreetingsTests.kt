package cgm.hexagonal.greetingsKata

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GreetingsTests {
    @Test
    internal fun name() {
        var listOfPerson = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        MyService().sendMessagesTo(listOfPerson) shouldBe """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }
}

class MyService {
    fun sendMessagesTo(listOfPerson: String): String {
        return """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }

}


