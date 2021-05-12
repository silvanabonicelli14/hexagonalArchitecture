package cgm.hexagonal.salestaxesKata.domain.usecases

import cgm.hexagonal.salestaxesKata.domain.doors.ArticleRepository
import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator

class ReceiptService(
    private val saleArticleRepository: ArticleRepository,
    private val priceCalculator: PriceCalculator
) {
    fun closeReceipt() {
        saleArticleRepository.getSale()
            .apply {
                priceCalculator.calculateTotalPrices(this)
                priceCalculator.closeReceipt(this)
            }
    }
}