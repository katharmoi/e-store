package com.cabify.domain.model

import java.math.BigDecimal

/**
 * Strategy pattern is used to implement discount.
 *
 * This interface represents the base for the discounts
 */
interface Discount {

    val code:String?

    fun apply(items: List<OrderItem>): BigDecimal
}