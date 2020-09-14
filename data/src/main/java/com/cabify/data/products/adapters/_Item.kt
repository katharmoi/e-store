package com.cabify.data.products.adapters

import com.cabify.data.products.model.DataItem
import com.cabify.domain.model.Item

fun Item.toData(): DataItem {
    return DataItem(
        code = this.code,
        name = this.name,
        price = this.price.toPlainString()
    )
}