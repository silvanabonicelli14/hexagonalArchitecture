package cgm.hexagonal.salestaxesKata.domain.models


interface  Tax{
    fun applyTaxFor(taxRule: TaxRule)
}

interface TaxRule{
    fun tax(tax : TaxImported)
    fun tax(tax: TaxCategory)
}

data class TaxImported(val article: Article, val countryForTax: Country) : Tax {
    override fun applyTaxFor(taxRule: TaxRule)  {
        taxRule.tax(this)
    }
}

data class TaxCategory(val article: Article, val countryForTax: Country) : Tax {
    override fun applyTaxFor(taxRule: TaxRule) {
        taxRule.tax(this)
    }
}