package cgm.hexagonal.greetingsKata.doors.repositories

import cgm.hexagonal.greetingsKata.domain.doors.PersonRepository
import cgm.hexagonal.greetingsKata.domain.models.Person
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CSVPersonRepository(private val sourcePerson: String): PersonRepository {
    override fun getPersons(): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(sourcePerson, csvFormat)
        return csvParser.map { record -> mapCsvToRecord(record)}
    }

    private fun mapCsvToRecord(record: CSVRecord) = Person(
        record[1].trimIndent(),
        record[0].trimIndent(),
        LocalDate.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        record[3].trimIndent()
    )
}