package cgm.hexagonal.greetingsKata.domain.doors

import cgm.hexagonal.greetingsKata.domain.models.Person

interface PersonRepository {
    fun getPersons(): List<Person>
}