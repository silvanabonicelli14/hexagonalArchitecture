package cgm.hexagonal.salestaxesKata.domain.usecases

import cgm.hexagonal.salestaxesKata.domain.models.*

val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
const val localTax = 0.10
const val importationTax = 0.05

object TaxCalculator {
    fun applyTax(article: Article, countryOfSale: Country): Double {
        var tax = 0.0
        tax.apply{
            tax += getTax(TaxCategory(article,countryOfSale))
            tax += getTax(TaxImported(article,countryOfSale))
        }
        return tax.roundedDouble
    }

    private fun getTax(tax: Tax): Double {
        var taxVal = 0.0

        tax.applyTaxFor( object: TaxRule {
            override fun tax(tax: TaxImported) {
                taxVal += if (tax.article.country != tax.countryForTax) { importationTax
                } else 0.0
            }
            override fun tax(tax: TaxCategory) {
                taxVal += if (listOfExemptions.contains(tax.article.category)) { 0.0} else localTax
            }
        })
        return taxVal
    }
}
