package cgm.hexagonal.salestaxesKata.doors.taxcalculator

import cgm.hexagonal.salestaxesKata.domain.TextReceiptTemplate
import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator
import cgm.hexagonal.salestaxesKata.domain.models.*
import kotlin.math.ceil

class TaxPriceCalculator(var receiptLines: MutableList<String>) : PriceCalculator {

    override fun closeReceipt(sale: Sale) {

        var receipt = Receipt(0.0,0.0,listOf<SaleArticle>())

        sale.salesList.forEach {
            it.tax += TaxCalculator().getTaxes(it.article, sale.country)
            it.taxedPrice = calculatePrice(it, it.tax)
            receipt.totalPrice += it.taxedPrice
            receipt.totalTax += ((it.tax * it.article.price * it.quantity)).roundedToNearest05
        }
        receipt.saleArticles = sale.salesList

        receiptLines = TextReceiptTemplate.printReceipt(receipt) as MutableList<String>
    }

    private fun calculatePrice(saleArticle: SaleArticle, tax: Double): Double {
        return ((saleArticle.article.price * saleArticle.quantity) * (1 + tax)).roundedToNearest05
    }
}

val Double.roundedToNearest05: Double
    get() {
        return ceil(this * 20) / 20;
    }

