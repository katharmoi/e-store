package io.appicenter.data.order.adapters

import io.appicenter.data.order.model.DataCustomer
import io.appicenter.domain.model.Customer

fun DataCustomer.toDomain(): Customer {
    return Customer(
        name = this.name,
        surname = this.surname,
        email = this.email,
        address = this.address,
        language = this.language
    )
}