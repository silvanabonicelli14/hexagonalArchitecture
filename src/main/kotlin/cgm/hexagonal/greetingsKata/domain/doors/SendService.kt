package cgm.hexagonal.greetingsKata.domain.doors

import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage

interface SendService {
    fun sendGreetings(listOfMessages: List<GreetingMessage>)
}