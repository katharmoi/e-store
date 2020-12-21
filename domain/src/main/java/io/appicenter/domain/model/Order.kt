package io.appicenter.domain.model

import java.math.BigDecimal
import java.util.*

data class Order(
    val id: Int = 0,
    val items: List<OrderItem>,
    val customer: Customer,
    val total: BigDecimal,
    val date: Date?
)