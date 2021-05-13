package cgm.hexagonal.salestaxesKata.domain.doors

import cgm.hexagonal.salestaxesKata.domain.models.Receipt
import cgm.hexagonal.salestaxesKata.domain.models.Sale

interface PriceCalculator {
    fun closeReceipt(sale: Sale): Receipt
}