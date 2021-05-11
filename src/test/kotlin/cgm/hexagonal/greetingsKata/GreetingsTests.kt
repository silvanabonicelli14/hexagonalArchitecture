package cgm.hexagonal.greetingsKata

import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.domain.models.Person
import cgm.hexagonal.greetingsKata.domain.BirthdayMessageTemplate
import cgm.hexagonal.greetingsKata.doors.repositories.PersonRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GreetingsTests {
    @Test
    internal fun name() {
        val sourcePerson = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        val today = LocalDate.of(2021, 10, 8)
        GreetingsService(PersonRepository(sourcePerson)).greetPersons(today) shouldBe listOf(
            GreetingMessage(
                """
                            Subject: Happy birthday!
                
                            Happy birthday, dear John!
                      """.trimIndent()
            )
        )
    }
}

class GreetingsService(private val personRepository: PersonRepository) {
    fun greetPersons(filterDate: LocalDate): List<GreetingMessage> {
        val listOfBirthdays = personRepository.findPerson(filterDate)
        return createGreetings(listOfBirthdays)
    }

    private fun createGreetings(listOfPerson: List<Person>): List<GreetingMessage> {
        var listOfGreetings = mutableListOf<GreetingMessage>()
         listOfPerson.forEach{listOfGreetings.add(BirthdayMessageTemplate.birthdayMessage(it.firstName))}
        return listOfGreetings
    }
}

