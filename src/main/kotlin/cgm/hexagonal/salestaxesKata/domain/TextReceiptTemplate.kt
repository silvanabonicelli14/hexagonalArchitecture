package cgm.hexagonal.salestaxesKata.domain

import cgm.hexagonal.salestaxesKata.domain.models.Receipt

object TextReceiptTemplate{

    fun printReceipt(receipt: Receipt): List<String> {
        val receiptLines = mutableListOf<String>()
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
        return receiptLines
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