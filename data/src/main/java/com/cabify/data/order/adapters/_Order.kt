package com.cabify.data.order.adapters

import com.cabify.data.order.model.DataOrder
import com.cabify.domain.model.Order

fun Order.toData(): DataOrder {
    return DataOrder(
        id = this.id,
        items = this.items,
        customer = this.customer.toData(),
        total = this.total.toPlainString(),
        date = this.date
    )
}