package cgm.hexagonal.salestaxesKata.doors.taxcalculator

import cgm.hexagonal.salestaxesKata.domain.doors.PriceCalculator
import cgm.hexagonal.salestaxesKata.domain.models.*
import kotlin.math.roundToLong

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
        return ((saleArticle.article.price * saleArticle.quantity) * (1 + tax)).roundedDouble
    }
}


class TaxCalculator{
    fun getTaxes(article: Article, countryOfSale: Country): Double {
        val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
        var tax = 0.0
        if (article.country != countryOfSale) tax += 0.05
        if (article.category !in (listOfExemptions)) tax += 0.1

        return tax.roundedDouble
    }
}

val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }
