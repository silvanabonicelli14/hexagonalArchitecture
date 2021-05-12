package cgm.hexagonal.salestaxesKata.doors.taxcalculator

import cgm.hexagonal.salestaxesKata.domain.models.Article
import cgm.hexagonal.salestaxesKata.domain.models.Category
import cgm.hexagonal.salestaxesKata.domain.models.Country

class TaxCalculator{
    fun getTaxes(article: Article, countryOfSale: Country): Double {
        val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
        var tax = 0.0
        if (article.country != countryOfSale) tax += 0.05
        if (article.category !in (listOfExemptions)) tax += 0.1

        return tax
    }
}