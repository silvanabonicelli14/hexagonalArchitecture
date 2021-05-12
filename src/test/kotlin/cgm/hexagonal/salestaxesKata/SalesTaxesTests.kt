package cgm.hexagonal.salestaxesKata

import cgm.hexagonal.salestaxesKata.domain.models.*
import cgm.hexagonal.salestaxesKata.domain.usecases.ReceiptService
import cgm.hexagonal.salestaxesKata.doors.repositories.SaleArticleRepository
import cgm.hexagonal.salestaxesKata.doors.taxcalculator.TaxPriceCalculator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.roundToLong

class SalesTaxesTests {
    private val expectedArt1 =  SaleArticle(Article("ART1",10.00, Category.Food, Country("SPA")),1.0)
    private val expectedArt2 =  SaleArticle(Article("ART2",47.50, Category.Other, Country("DEU")),1.0)
    init {
        expectedArt1.taxedPrice = 10.50
        expectedArt1.tax = 54.65
        expectedArt2.taxedPrice = 54.65
        expectedArt2.tax = 54.65
    }
    @Test
    internal fun `create receipt`() {

        val dataSourceArticle = """
            cod, price, category, country, quantity
            ART1, 10.00, Food, SPA, 1
            ART2, 47.50, Other, DEU, 1
        """.trimIndent()

        var receipt = Receipt(0.0,0.0,listOf<SaleArticle>())
        ReceiptService(SaleArticleRepository(dataSourceArticle), TaxPriceCalculator(receipt)).closeReceipt()

        receipt.totalPrice shouldBe 65.15
        receipt.totalTax shouldBe 7.65
        receipt.saleArticles.size shouldBe 2
        receipt.saleArticles[0].taxedPrice shouldBe 10.50
        receipt.saleArticles[1].taxedPrice shouldBe 54.65
        //receipt shouldBe   Receipt(65.15,7.65,listOf(expectedArt1,expectedArt2))
    }
}
