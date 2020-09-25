package com.cabify.domain.model

import java.math.BigDecimal

/**
 * Represents discounts for the bulk orders
 *
 * @param [code] code of the item that the discount will be applied
 *
 * @param [minQty] minimum number that is needed for the discount to be applicable
 */
class BulkPurchaseDiscount(
    override val code: String,
    private val discount: BigDecimal,
    private val minQty: Int
) : Discount {

    override fun apply(items: List<OrderItem>): BigDecimal {

        var totalDiscount = BigDecimal.ZERO
        items.forEach {
            if (it.item.code == code && it.count >= minQty) {
                totalDiscount = discount.multiply(BigDecimal(it.count))
            }
        }
        return totalDiscount
    }
}