package cgm.hexagonal.salestaxesKata.doors.taxcalculator

import cgm.hexagonal.salestaxesKata.domain.TextReceiptTemplate
import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator
import cgm.hexagonal.salestaxesKata.domain.models.*
import kotlin.math.ceil
import kotlin.math.roundToLong

class ReceiptPriceCalculator(var receiptLines: MutableList<String>) : PriceCalculator {

    override fun closeReceipt(sale: Sale) {

        val receipt = Receipt(0.0,0.0,listOf())

        sale.salesList.forEach {
            it.tax += TaxCalculator().applyTax(it.article, sale.country)
            it.taxedPrice = calculateTaxedPrice(it, it.tax)
            receipt.totalPrice += it.taxedPrice
            receipt.totalTax += ((it.tax * it.article.price * it.quantity)).roundedToNearest05
        }
        receipt.saleArticles = sale.salesList
        receiptLines = TextReceiptTemplate.printReceipt(receipt) as MutableList<String>
    }

    private fun calculateTaxedPrice(saleArticle: SaleArticle, tax: Double): Double {
        return ((saleArticle.article.price * saleArticle.quantity) * (1 + tax)).roundedToNearest05
    }
}

val Double.roundedToNearest05: Double
    get() {
        return ceil(this * 20) / 20
    }
val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }

