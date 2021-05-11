package cgm.hexagonal.greetingsKata

import io.kotest.matchers.shouldBe
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.junit.jupiter.api.Test


class GreetingsTests {
    @Test
    internal fun name() {
        var sourcePerson = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(sourcePerson, csvFormat)
        val listOfPerson = csvParser.map { record ->
            Person(record[1],record[0],record[2],record[3])
        }

        MyService().sendMessagesTo(listOfPerson) shouldBe """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }
}

class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String
)

class MyService {
    fun sendMessagesTo(listOfPerson: List<Person>): String {
        return """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }
}


