package io.appicenter.domain.model

import java.math.BigDecimal

/**
 * Represents discounts for the bulk orders
 *
 * @param [code] code of the item that the discount will be applied
 */
class TwoForOneDiscount(
    override val code: String
) : Discount {
    override fun apply(items: List<OrderItem>): BigDecimal {
        var discount = BigDecimal.ZERO
        items.forEach {
            if (code == it.item.code) discount += BigDecimal(it.count / 2).multiply(it.item.price)
        }
        return discount
    }

}