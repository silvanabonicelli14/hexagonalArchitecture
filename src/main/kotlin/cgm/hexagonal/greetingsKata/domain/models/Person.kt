package cgm.hexagonal.greetingsKata.domain.models

import java.time.LocalDate

data class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String
){
    fun isPersonBirthday(today: LocalDate): Boolean {
       return dateOfBirth.month == today.month && dateOfBirth.dayOfMonth == today.dayOfMonth
    }
}