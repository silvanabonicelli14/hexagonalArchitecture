package cgm.hexagonal.salestaxesKata

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SalesTaxesTests {
    @Test
    internal fun `create receipt`() {
       ReceiptService().receipt() shouldBe Receipt(1.0,1.0)
    }
}
class ReceiptService {
    fun receipt(): Receipt {
        return Receipt(1.0,1.0)
    }
}
data class Receipt(val totalPrice: Double, val totalTax: Double) {
    fun receipt(): String {
        return ""
    }
}
