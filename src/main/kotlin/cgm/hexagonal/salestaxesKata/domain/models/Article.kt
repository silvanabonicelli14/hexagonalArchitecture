package cgm.hexagonal.salestaxesKata.domain.models

data class Article(
    val code: String,
    val price: Double,
    val category: Category,
    val country: Country
)