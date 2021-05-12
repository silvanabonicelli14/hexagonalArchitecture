package cgm.hexagonal.salestaxesKata.domain.doors

import cgm.hexagonal.salestaxesKata.domain.models.Sale

interface ArticleRepository {
    fun getSale(): Sale
}