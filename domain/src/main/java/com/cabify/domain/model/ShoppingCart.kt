package com.cabify.domain.model

import java.math.BigDecimal

/**
 * Represents customer's shopping cart
 */
class ShoppingCart(
    private vararg val discounts: Discount
) {

    //used backing property to return immutable list
    private val _cart: MutableList<OrderItem> = mutableListOf()
    val cart: List<OrderItem> = _cart

    //used backing property to return immutable list
    private val _appliedDiscounts: MutableList<String> = mutableListOf()
    val appliedDiscounts: List<String> = _appliedDiscounts


    //To provide immutability prevents set
    var total: BigDecimal = BigDecimal.ZERO
        private set

    //To provide immutability prevents set
    var totalDiscounts: BigDecimal = BigDecimal.ZERO
        private set

    //To provide immutability prevents set
    var totalAfterDiscounts: BigDecimal = BigDecimal.ZERO
        private set

    val isEmpty: Boolean
        get() = cart.isEmpty()

    /**
     * Returns the number of unique items in the cart
     */
    val size: Int
        get() = cart.size

    /**
     * Calculates total without discounts
     */
    private fun total(): BigDecimal {
        return cart.fold(BigDecimal.ZERO) { acc, orderItem ->
            acc + orderItem.item.price.multiply(
                BigDecimal(orderItem.count)
            )
        }
    }

    /**
     * Calculates total discount and populates
     * applied discount
     */
    private fun totalDiscount(): BigDecimal {

        var totalDiscount = BigDecimal.ZERO

        for (discount in discounts.distinct()) {
            if (discount.apply(cart) > BigDecimal.ZERO) {
                totalDiscount += discount.apply(cart)
                _appliedDiscounts.add(discount::class.java.simpleName)
            }
        }

        return totalDiscount
    }

    /**
     * Calculates total after discounts
     */
    private fun finalTotal() {
        total = total()
        totalDiscounts = totalDiscount()
        totalAfterDiscounts = total - totalDiscounts
    }

    /**
     * Adds new item if it does not exist else increments
     * item's quantity by one & recalculates total
     */
    fun add(item: Item) {
        cart.find { it.item.code == item.code }.apply {
            if (this != null) this.count++
            else _cart.add(OrderItem(item, 1))
        }

        finalTotal()
    }

    /**
     * Gets the given product if exists
     *
     * @param [item] product to be get
     */

    fun get(item: Item): OrderItem? {
        return cart.find { it.item.code == item.code }?.copy()
    }

    /**
     * removes given item from cart & recalculates total
     *
     * @param [item] : item to be removed
     */
    fun delete(item: Item) {
        _cart.remove(cart.find { it.item.code == item.code })
        finalTotal()
    }

    /**
     * Updates items qty  if exists in the cart to the given number & recalculates total
     *
     * if [qty] is zero removes item from cart
     * if [qty] is bigger than zero updates item count
     * if [qty] is smaller than zero does nothing
     *
     * @param [item] item to be updated
     * @param [qty] the number that the item count will be updated
     */
    fun update(item: Item, qty: Int) {
        if (qty < 0) return

        cart.find { it.item.code == item.code }
            .let {
                if (it != null) {
                    if (qty > 0) it.count = qty
                    else if (qty == 0) delete(it.item)
                }
            }

        finalTotal()
    }

    /**
     * Sets cart to it's initial empty state
     */
    fun clear() {
        _cart.clear()
        _appliedDiscounts.clear()
        total = BigDecimal.ZERO
        totalAfterDiscounts = BigDecimal.ZERO
        totalDiscounts = BigDecimal.ZERO
    }

    override fun toString(): String {
        return "items: $cart \napplied discounts: $appliedDiscounts \ntotal: $total" +
                " \ntotal discounts: $totalDiscounts \ntotal after discounts: $totalAfterDiscounts"
    }


}