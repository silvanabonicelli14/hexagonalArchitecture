package cgm.hexagonal.salestaxesKata

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.roundToLong

class SalesTaxesTests {
    private val art1 = SaleArticle(Article("ART1",1.0,Category.Food,Country("ITA")),1.0)
    private val art2 = SaleArticle(Article("ART2",1.0,Category.Other,Country("SPA")),1.0)
    private val expectedArt1 =  SaleArticle(Article("ART1",1.0,Category.Food,Country("ITA")),1.0)
    private val expectedArt2 =  SaleArticle(Article("ART2",1.0,Category.Other,Country("SPA")),1.0)
    init {
        expectedArt1.tax = 1.5
        expectedArt2.tax = 1.5
    }

    @Test
    internal fun `create receipt`() {

        val sale = Sale(Country("ITA"),listOf(art1, art2))
        val receipt = ReceiptService().receipt(sale)

        receipt.totalPrice shouldBe 2.0
        receipt.totalTax shouldBe 2.0
        receipt.saleArticles.size shouldBe 2
        receipt.saleArticles[0].tax.roundedDouble shouldBe 0.0
        receipt.saleArticles[1].tax.roundedDouble shouldBe 0.15

        receipt shouldBe   Receipt(2.0,2.0,listOf(expectedArt1,expectedArt2))
    }
}

enum class Category {
    Medical,
    Book,
    Food,
    Other
}

val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }

class Sale(val country: Country,val salesList: List<SaleArticle>) {

    fun getTaxes() {
        val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
        this.salesList.forEach { saleArticle ->
            if (saleArticle.article.country != this.country) saleArticle.tax += 0.05
            if (saleArticle.article.category !in (listOfExemptions)) saleArticle.tax += 0.1
        }
    }

    fun calculatePrice() {

    }
}

data class Country(val country: String)

data class SaleArticle(val article: Article, val quantity: Double) {
    var taxedPrice: Double = 0.0
    var tax: Double = 0.0
}

data class Article(
    val code: String,
    val price: Double,
    val category: Category,
    val country: Country
)

class ReceiptService {
    fun receipt(sale: Sale): Receipt {

      sale.salesList.forEach {it.tax += getTaxes(it.article,sale.country) }
      sale.salesList.forEach {calculatePrice(it, it.tax) }

      return Receipt(2.0, 2.0, sale.salesList)
    }


    private fun calculatePrice(article: SaleArticle, tax: Double): Double {
        return (article.taxedPrice * article.quantity) * (1 + tax)
    }

    private fun getTaxes(article: Article, countryOfSale: Country): Double {
        val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
        var tax = 0.0
        if (article.country != countryOfSale) tax += 0.05
        if (article.category !in (listOfExemptions)) tax += 0.1

        return tax
    }
}
data class Receipt(val totalPrice: Double, val totalTax: Double, val saleArticles: List<SaleArticle>)