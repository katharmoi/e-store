package com.cabify.domain.model

/**
 * This class represents data for an individual item
 * in customer's shopping cart. To prevent user from having
 * multiple instances of an item it includes a count property
 */
data class OrderItem(
    val item: Item,
    var count: Int
){
    override fun toString(): String {
        return "$item\ncount: $count"
    }
}