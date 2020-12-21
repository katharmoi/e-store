package io.appicenter.data.order.adapters

import io.appicenter.data.order.model.DataOrder
import io.appicenter.domain.model.Order

fun DataOrder.toDomain(): Order {
    return Order(
        id = this.id,
        items = this.items,
        customer = this.customer.toDomain(),
        total = this.total.toBigDecimal(),
        date = this.date
    )
}