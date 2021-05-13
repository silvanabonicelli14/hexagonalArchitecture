package cgm.hexagonal.salestaxesKata

import cgm.hexagonal.salestaxesKata.domain.usecases.ReceiptPriceCalculator
import cgm.hexagonal.salestaxesKata.domain.usecases.ReceiptService
import cgm.hexagonal.salestaxesKata.doors.receiptprinter.ReceiptTextPrinter
import cgm.hexagonal.salestaxesKata.doors.repositories.SaleArticleRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SalesTaxesTests {

    @Test
    internal fun `create receipt`() {

        val dataSourceArticle = """
            cod, price, category, country, quantity
            ART1, 10.00, Food, SPA, 1
            ART2, 47.50, Other, DEU, 1
        """.trimIndent()
        val receiptLines = mutableListOf<String>()
        val taxPriceCalculator = ReceiptPriceCalculator()
        val receiptTextPrinter = ReceiptTextPrinter(receiptLines)
        ReceiptService(
            SaleArticleRepository(dataSourceArticle),
            ReceiptPriceCalculator(),
            receiptTextPrinter
        ).closeReceipt("ITA")

        receiptTextPrinter.receiptLines shouldBe listOf(
            """
            ******************************
                Total receipt 65.15
            ******************************    
            """.trimIndent(),
            """
                Total tax 7.65
            """.trimIndent(),
            """
                Article: ART1 - Quantity: 1 - Total: 10.5
            """.trimIndent(),
            """
                Article: ART2 - Quantity: 1 - Total: 54.65
            """.trimIndent()
        )
    }
}
