package com.cabify.data.order.adapters

import com.cabify.data.order.model.DataOrder
import com.cabify.domain.model.Order

fun DataOrder.toDomain(): Order {
    return Order(
        id = this.id,
        items = this.items,
        customer = this.customer.toDomain(),
        total = this.total.toBigDecimal(),
        date = this.date
    )
}