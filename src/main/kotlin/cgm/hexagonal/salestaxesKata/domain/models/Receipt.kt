package cgm.hexagonal.salestaxesKata.domain.models

data class Receipt(var totalPrice: Double, var totalTax: Double, var saleArticles: List<SaleArticle>)