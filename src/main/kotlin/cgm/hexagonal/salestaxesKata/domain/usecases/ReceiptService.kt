package cgm.hexagonal.salestaxesKata.domain.usecases

import cgm.hexagonal.salestaxesKata.domain.doors.ArticleRepository
import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator
import cgm.hexagonal.salestaxesKata.domain.models.Country
import cgm.hexagonal.salestaxesKata.doors.receiptprinter.ReceiptPrinter

class ReceiptService(
    private val saleArticleRepository: ArticleRepository,
    private val priceCalculator: PriceCalculator,
    private val receiptPrinter: ReceiptPrinter
) {
    fun closeReceipt(country: String) {
        saleArticleRepository.getSale(Country(country))
            .run { priceCalculator.closeReceipt(this)}
            .run { receiptPrinter.printReceipt(this)}
    }
}