package cgm.hexagonal.greetingsKata.doors.sendservices

import cgm.hexagonal.greetingsKata.domain.BirthdayMessageTemplate
import cgm.hexagonal.greetingsKata.domain.doors.SendService
import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage
import cgm.hexagonal.greetingsKata.domain.models.Person

class GreetingsSendService(private val listOfStringMessages: MutableList<String>): SendService
{
    override fun sendGreetings(listOfMessages: List<GreetingMessage>) {
        listOfMessages.forEach{listOfStringMessages.add(it.subject)}
    }
}