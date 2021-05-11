package cgm.hexagonal.greetingsKata

import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.doors.repositories.PersonRepository
import cgm.hexagonal.greetingsKata.doors.sendservices.GreetingsSendService
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
        GreetingsService(PersonRepository(sourcePerson),GreetingsSendService()).greetPersons(today) shouldBe listOf(
            GreetingMessage(
                """
                            Subject: Happy birthday!
                
                            Happy birthday, dear John!
                      """.trimIndent()
            )
        )
    }
}

class GreetingsService(private val personRepository: PersonRepository, private val sendService: GreetingsSendService) {
    fun greetPersons(filterDate: LocalDate): List<GreetingMessage> =
        personRepository.getPersons()
            .filter { person -> person.isPersonBirthday(filterDate)}
            .run{sendService.sendGreetings(this)}
}

