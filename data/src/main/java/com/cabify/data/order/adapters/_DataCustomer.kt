package com.cabify.data.order.adapters

import com.cabify.data.order.model.DataCustomer
import com.cabify.domain.model.Customer

fun DataCustomer.toDomain(): Customer {
    return Customer(
        name = this.name,
        surname = this.surname,
        email = this.email,
        address = this.address,
        language = this.language
    )
}