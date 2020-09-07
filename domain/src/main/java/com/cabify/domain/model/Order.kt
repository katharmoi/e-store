package com.cabify.domain.model

import java.math.BigDecimal
import java.util.*

class Order(
    val id: Int?,
    val items: List<Item>,
    val customer: Customer?,
    val total: BigDecimal,
    val date: Date?
)