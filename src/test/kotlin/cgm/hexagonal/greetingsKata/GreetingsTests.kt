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
        val sourcePerson = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/05/11, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        GreetingsService(PersonRepository(sourcePerson)).greetPersons(LocalDate.now()) shouldBe
                listOf("""
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent())
    }
}

class GreetingsService(private val personRepository: PersonRepository) {
    fun greetPersons(filterDate: LocalDate): List<String> {
        val listOfBirthdays = personRepository.findPerson(filterDate)
        return sendMessagesTo(listOfBirthdays)
    }

    private fun sendMessagesTo(listOfPerson: List<Person>): List<String> {
        var listOfGreetings = mutableListOf<String>()
         listOfPerson.forEach{listOfGreetings.add(createGreeting(it.firstName))}
        return listOfGreetings
    }

    private fun createGreeting(firstName: String): String =
            """
                Subject: Happy birthday!
    
                Happy birthday, dear $firstName!
            """.trimIndent()

}

class PersonRepository(private val sourcePerson: String) {
    fun findPerson(filterDate: LocalDate): List<Person> {
        return getPersons().filter { person ->
            person.dateOfBirth.month == filterDate.month
                    && person.dateOfBirth.dayOfMonth == filterDate.dayOfMonth
        }
    }

    private fun getPersons(): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(sourcePerson, csvFormat)
        return csvParser.map { record ->
            Person(
                record[1].trimIndent(),
                record[0].trimIndent(),
                LocalDate.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                record[3].trimIndent()
            )
        }
    }
}

class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String
)
