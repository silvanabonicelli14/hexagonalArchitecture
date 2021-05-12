package cgm.hexagonal.salestaxesKata.doors.taxcalculator

import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator
import cgm.hexagonal.salestaxesKata.domain.models.*
import kotlin.math.ceil
import kotlin.math.roundToLong

class TaxPriceCalculator(private val receipt: Receipt) : PriceCalculator {

    override fun calculateTotalPrices(sale: Sale) {
        sale.salesList.forEach {
            it.tax += TaxCalculator().getTaxes(it.article, sale.country)
            it.taxedPrice = calculatePrice(it, it.tax)
        }
    }

    override fun closeReceipt(sale: Sale) {
        sale.salesList.forEach {
            receipt.totalPrice += it.taxedPrice
            receipt.totalTax += ((it.tax * it.article.price * it.quantity)).roundedToNearest05
        }
        receipt.saleArticles = sale.salesList
    }

    private fun calculatePrice(saleArticle: SaleArticle, tax: Double): Double {
        return ((saleArticle.article.price * saleArticle.quantity) * (1 + tax)).roundedToNearest05
    }
}

val Double.roundedToNearest05: Double
    get() {
        return ceil(this * 20) / 20;
    }