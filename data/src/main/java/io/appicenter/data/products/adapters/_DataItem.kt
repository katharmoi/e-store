package io.appicenter.data.products.adapters

import io.appicenter.data.products.model.DataItem
import io.appicenter.domain.model.Item
import java.math.BigDecimal

fun DataItem.toDomain(): Item {
    return Item(
        code = this.code,
        name = this.name,
        price = BigDecimal(this.price)
    )
}