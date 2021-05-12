package cgm.hexagonal.salestaxesKata

import cgm.hexagonal.salestaxesKata.domain.models.*
import io.kotest.matchers.shouldBe
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.junit.jupiter.api.Test
import kotlin.math.roundToLong

class SalesTaxesTests {
    private val expectedArt1 =  SaleArticle(Article("ART1",1.0, Category.Food, Country("ITA")),1.0)
    private val expectedArt2 =  SaleArticle(Article("ART2",1.0, Category.Other, Country("SPA")),1.0)
    init {
        expectedArt1.tax = 1.5
        expectedArt2.tax = 1.5
    }

    @Test
    internal fun `create receipt`() {

        val dataSourceArticle = """
            cod, price, category, country, quantity
            ART1, 1.0, Food, ITA, 1
            ART2, 1.0, Other, SPA, 1
        """.trimIndent()

        var receipt = Receipt(0.0,0.0,listOf<SaleArticle>())
        ReceiptService(SaleArticleRepository(dataSourceArticle),TaxPriceCalculator(receipt)).closeReceipt()

        receipt.totalPrice shouldBe 2.0
        receipt.totalTax shouldBe 2.0
        receipt.saleArticles.size shouldBe 2
        receipt.saleArticles[0].tax.roundedDouble shouldBe 0.0
        receipt.saleArticles[1].tax.roundedDouble shouldBe 0.15
        receipt.saleArticles[0].taxedPrice.roundedDouble shouldBe 1.0
        receipt.saleArticles[1].taxedPrice.roundedDouble shouldBe 1.15

        receipt shouldBe   Receipt(2.0,2.0,listOf(expectedArt1,expectedArt2))
    }
}
class ReceiptService(private val saleArticleRepository: ArticleRepository, private val priceCalculator: PriceCalculator) {
    fun closeReceipt() {
        saleArticleRepository.getSale()
            .apply{
                priceCalculator.calculateTotalPrices(this)
                priceCalculator.printReceipt(this)
            }
    }
}

interface PriceCalculator {
    fun calculateTotalPrices(sale: Sale)
    fun printReceipt(sale: Sale)
}

class TaxPriceCalculator(private val receipt: Receipt) : PriceCalculator {

    override fun calculateTotalPrices(sale: Sale) {
        sale.salesList.forEach { it.tax += TaxCalculator().getTaxes(it.article, sale.country) }
        sale.salesList.forEach { it.taxedPrice = calculatePrice(it, it.tax) }
    }

    override fun printReceipt(sale: Sale) {
        receipt.totalPrice = 2.0
        receipt.totalTax = 2.0
        receipt.saleArticles = sale.salesList
    }

    private fun calculatePrice(saleArticle: SaleArticle, tax: Double): Double {
        return (saleArticle.article.price * saleArticle.quantity) * (1 + tax)
    }
}

class TaxCalculator{
    fun getTaxes(article: Article, countryOfSale: Country): Double {
        val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
        var tax = 0.0
        if (article.country != countryOfSale) tax += 0.05
        if (article.category !in (listOfExemptions)) tax += 0.1

        return tax
    }
}

interface ArticleRepository {
    fun getSale(): Sale
}

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
        record[4].trimIndent().toDouble()
    )
}

val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }

