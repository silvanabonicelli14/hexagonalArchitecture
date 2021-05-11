package cgm.hexagonal.greetingsKata.domain.usecases

import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.doors.repositories.PersonRepository
import cgm.hexagonal.greetingsKata.doors.sendservices.GreetingsSendService
import java.time.LocalDate

class GreetingsService(private val personRepository: PersonRepository, private val sendService: GreetingsSendService) {
    fun greetPersons(filterDate: LocalDate): List<GreetingMessage> =
        personRepository.getPersons()
            .filter { person -> person.isPersonBirthday(filterDate)}
            .run{sendService.sendGreetings(this)}
}