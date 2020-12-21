package io.appicenter.data.order.adapters

import io.appicenter.data.order.model.DataOrder
import io.appicenter.domain.model.Order

fun Order.toData(): DataOrder {
    return DataOrder(
        id = this.id,
        items = this.items,
        customer = this.customer.toData(),
        total = this.total.toPlainString(),
        date = this.date
    )
}