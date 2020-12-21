package io.appicenter.domain.model

import java.math.BigDecimal

/**
 * This class represents data for an individual item
 * in customer's shopping cart. To prevent user from having
 * multiple instances of an item it includes a count property
 */
data class OrderItem(
    val item: Item,
    var count: Int,
    var discount: BigDecimal? = null,
    var applicableDiscount: String? = null
) {
    override fun toString(): String {
        return "\n$item\ncount: $count\ndiscount: $discount\napplicable discounts: $applicableDiscount"
    }
}