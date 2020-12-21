package io.appicenter.domain.model

import java.math.BigDecimal

/**
 * This class represents a physical item
 */
data class Item(
    val code: String,
    val name: String,
    val price: BigDecimal
) {
    override fun toString(): String {
        return "item name: $name \ncode: $code \nprice: $price "
    }
}