package cgm.hexagonal.greetingsKata.doors.repositories

import cgm.hexagonal.greetingsKata.domain.models.Person
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PersonRepository(private val sourcePerson: String) {
    fun findPerson(filterDate: LocalDate): List<Person> {
        return getPersons().filter { person ->
            person.dateOfBirth.month == filterDate.month
                    && person.dateOfBirth.dayOfMonth == filterDate.dayOfMonth
        }
    }

    private fun getPersons(): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(sourcePerson, csvFormat)
        return csvParser.map { record ->
            Person(
                record[1].trimIndent(),
                record[0].trimIndent(),
                LocalDate.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                record[3].trimIndent()
            )
        }
    }
}