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

        GreetingsService(PersonRepository(sourcePerson)).greetPersons(LocalDate.now()) shouldBe """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }
}


class GreetingsService(private val personRepository: PersonRepository) {
    fun greetPersons(filterDate: LocalDate): String {
        val listOfPerson = personRepository.getPersons()
        val listOfBirthdays = personRepository.findPerson(listOfPerson, filterDate)
        return sendMessagesTo(listOfBirthdays)
    }

    private fun sendMessagesTo(listOfPerson: List<Person>): String {
        return """
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent()
    }

}

class PersonRepository(private val sourcePerson: String) {
    fun getPersons(): List<Person> {
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

    fun findPerson(listOfPerson: List<Person>, filterDate: LocalDate): List<Person> {
        return listOfPerson.filter { person -> person.dateOfBirth.month == filterDate.month
                && person.dateOfBirth.dayOfMonth == filterDate.dayOfMonth
        }
    }
}

class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String
)
