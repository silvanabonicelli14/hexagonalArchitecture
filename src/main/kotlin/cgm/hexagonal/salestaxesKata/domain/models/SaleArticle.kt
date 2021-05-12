package cgm.hexagonal.salestaxesKata.domain.models

data class SaleArticle(val article: Article, val quantity: Double) {
    var taxedPrice: Double = 0.0
    var tax: Double = 0.0
}