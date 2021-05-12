package cgm.hexagonal.salestaxesKata

import cgm.hexagonal.salestaxesKata.domain.models.*
import cgm.hexagonal.salestaxesKata.domain.usecases.ReceiptService
import cgm.hexagonal.salestaxesKata.doors.repositories.SaleArticleRepository
import cgm.hexagonal.salestaxesKata.doors.taxcalculator.TaxPriceCalculator
import io.kotest.matchers.shouldBe
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
        ReceiptService(SaleArticleRepository(dataSourceArticle), TaxPriceCalculator(receipt)).closeReceipt()

        receipt.totalPrice shouldBe 2.0
        receipt.totalTax shouldBe 2.0
        receipt.saleArticles.size shouldBe 2
        receipt.saleArticles[0].tax shouldBe 0.0
        receipt.saleArticles[1].tax shouldBe 0.15
        receipt.saleArticles[0].taxedPrice shouldBe 1.0
        receipt.saleArticles[1].taxedPrice shouldBe 1.15

        receipt shouldBe   Receipt(2.0,2.0,listOf(expectedArt1,expectedArt2))
    }
}
