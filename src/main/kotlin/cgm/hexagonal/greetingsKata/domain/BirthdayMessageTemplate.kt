package cgm.hexagonal.greetingsKata.domain

import cgm.hexagonal.greetingsKata.domain.models.GreetingMessage

object BirthdayMessageTemplate {
    fun birthdayMessage(firstName: String): GreetingMessage =
        GreetingMessage(
            """
                Subject: Happy birthday!
    
                Happy birthday, dear $firstName!
            """.trimIndent()
        )

}