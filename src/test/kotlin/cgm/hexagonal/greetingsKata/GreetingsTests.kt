package cgm.hexagonal.greetingsKata

import io.kotest.matchers.shouldBe
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class GreetingsTests {
    @Test
    internal fun name() {
        var sourcePerson = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        val listOfPerson = getListOfPerson(sourcePerson)

        val listOfBirthdays = findPerson(listOfPerson)


        SendGreetingsService().sendMessagesTo(listOfBirthdays) shouldBe """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }

    private fun getListOfPerson(sourcePerson: String): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(sourcePerson, csvFormat)
        return csvParser.map { record ->
            Person(
                record[1],
                record[0],
                LocalDate.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                record[3]
            )
        }
    }

    private fun findPerson(listOfPerson: List<Person>): List<Person> {
        val today = LocalDate.now()
        return listOfPerson.filter { person -> person.dateOfBirth.month == today.month
                                    && person.dateOfBirth.dayOfMonth == today.dayOfMonth
        }
    }
}

class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String
)

class SendGreetingsService {
    fun sendMessagesTo(listOfPerson: List<Person>): String {
        return """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }
}


