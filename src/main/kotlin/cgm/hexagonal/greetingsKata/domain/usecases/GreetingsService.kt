package cgm.hexagonal.greetingsKata.domain.usecases

import cgm.hexagonal.greetingsKata.domain.BirthdayMessageTemplate
import cgm.hexagonal.greetingsKata.domain.doors.PersonRepository
import cgm.hexagonal.greetingsKata.domain.doors.SendService
import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.domain.models.Person
import cgm.hexagonal.greetingsKata.doors.repositories.CSVPersonRepository
import cgm.hexagonal.greetingsKata.doors.sendservices.GreetingsSendService
import java.time.LocalDate

class GreetingsService(private val personRepository: PersonRepository, private val sendService: SendService) {
    fun greetPersons(filterDate: LocalDate): Unit = personRepository
            .getPersons()
            .run {createGreetingMessages(this,filterDate)}
            .run{sendService.sendGreetings(this)}

    private fun createGreetingMessages(listOfPerson: List<Person>, filterDate: LocalDate): List<GreetingMessage> {
        return listOfPerson
            .filter { person -> person.isPersonBirthday(filterDate) }
            .map { person -> BirthdayMessageTemplate.birthdayMessage(person.firstName) }
            .toList()
    }
}
