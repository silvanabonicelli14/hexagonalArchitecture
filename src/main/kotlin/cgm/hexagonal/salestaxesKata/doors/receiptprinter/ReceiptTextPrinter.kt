package cgm.hexagonal.salestaxesKata.doors.receiptprinter

import cgm.hexagonal.salestaxesKata.domain.models.Receipt

interface ReceiptPrinter{
    fun printReceipt(receipt: Receipt)
}

class ReceiptTextPrinter(val receiptLines: MutableList<String>) : ReceiptPrinter {
    override fun printReceipt(receipt: Receipt) {
        receiptLines.add(getTotalPriceLine(receipt.totalPrice.toString()))
        receiptLines.add(getTotalTaxLine(receipt.totalTax.toString()))
        receipt.saleArticles.forEach {
            receiptLines.add(
                getArticleLine(
                    it.article.code,
                    it.quantity.toString(),
                    it.taxedPrice.toString()
                )
            )
        }
    }

    private fun getTotalPriceLine(totalLine: String) =
            """
            ******************************
                Total receipt $totalLine
            ******************************    
            """.trimIndent()

    private fun getTotalTaxLine(totalTax: String): String =
            """
                Total tax $totalTax
            """.trimIndent()

    private fun getArticleLine(articleCode: String, quantity: String, price: String): String =
            """
                Article: $articleCode - Quantity: $quantity - Total: $price
            """.trimIndent()

}