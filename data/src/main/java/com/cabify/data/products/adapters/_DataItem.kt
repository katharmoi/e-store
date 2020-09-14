package com.cabify.data.products.adapters

import com.cabify.data.products.model.DataItem
import com.cabify.domain.model.Item
import java.math.BigDecimal

fun DataItem.toDomain(): Item {
    return Item(
        code = this.code,
        name = this.name,
        price = BigDecimal(this.price)
    )
}