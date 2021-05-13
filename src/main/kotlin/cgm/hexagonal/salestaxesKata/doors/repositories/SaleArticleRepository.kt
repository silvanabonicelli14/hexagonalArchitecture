package cgm.hexagonal.salestaxesKata.doors.repositories

import cgm.hexagonal.salestaxesKata.domain.doors.ArticleRepository
import cgm.hexagonal.salestaxesKata.domain.models.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

class SaleArticleRepository(private val dataSourceArticle: String) : ArticleRepository {

    override fun getSale(): Sale {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csvParser = CSVParser.parse(dataSourceArticle, csvFormat)
        val listOfSaleArticles: List<SaleArticle> = csvParser.map { record ->
            formatSale(record)
        }
        return Sale(Country("ITA"), listOfSaleArticles)
    }

    private fun formatSale(record: CSVRecord) = SaleArticle(
        Article(
            record[0].trimIndent(),
            record[1].trimIndent().toDouble(),
            Category.valueOf(record[2].trimIndent()),
            Country(record[3].trimIndent())
        ),
        record[4].trimIndent().toInt()
    )
}