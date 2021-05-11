package cgm.hexagonal.greetingsKata.doors.sendservices

import cgm.hexagonal.greetingsKata.domain.BirthdayMessageTemplate
import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.domain.models.Person

class GreetingsSendService()
{
    fun sendGreetings(listOfPerson: List<Person>): List<GreetingMessage> {
        var listOfGreetings = mutableListOf<GreetingMessage>()
        listOfPerson.forEach{listOfGreetings.add(BirthdayMessageTemplate.birthdayMessage(it.firstName))}
        return listOfGreetings
    }
}