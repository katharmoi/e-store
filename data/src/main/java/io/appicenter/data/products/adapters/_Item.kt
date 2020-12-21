package io.appicenter.data.products.adapters

import io.appicenter.data.products.model.DataItem
import io.appicenter.domain.model.Item

fun Item.toData(): DataItem {
    return DataItem(
        code = this.code,
        name = this.name,
        price = this.price.toPlainString()
    )
}