package cgm.hexagonal.salestaxesKata.domain.doors

import cgm.hexagonal.salestaxesKata.domain.models.Sale

interface PriceCalculator {
    fun calculateTotalPrices(sale: Sale)
    fun closeReceipt(sale: Sale)
}