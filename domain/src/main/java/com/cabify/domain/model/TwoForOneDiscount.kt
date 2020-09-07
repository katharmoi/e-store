package com.cabify.domain.model

import java.math.BigDecimal

/**
 * Represents discounts for the bulk orders
 *
 * @param [codes] code of the item that the discount will be applied
 */
class TwoForOneDiscount(
    private val code: String
) : Discount {
    override fun apply(items: List<OrderItem>): BigDecimal {
        var discount = BigDecimal.ZERO
        items.forEach {
            if (code == it.item.code) discount += BigDecimal(it.count / 2).multiply(it.item.price)
        }
        return discount
    }

}