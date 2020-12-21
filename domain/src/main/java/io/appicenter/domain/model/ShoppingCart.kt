package io.appicenter.domain.model

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
    private val _appliedDiscounts: MutableMap<String, BigDecimal> = hashMapOf()
    val appliedDiscounts: Map<String, BigDecimal> = _appliedDiscounts


    //prevents set
    var total: BigDecimal = BigDecimal.ZERO
        private set

    //prevents set
    var totalDiscounts: BigDecimal = BigDecimal.ZERO
        private set

    //prevents set
    var totalAfterDiscounts: BigDecimal = BigDecimal.ZERO
        private set

    val isEmpty: Boolean
        @Synchronized get() = _cart.isEmpty()

    /**
     * Returns the number of unique items in the cart
     */
    val size: Int
        @Synchronized get() = _cart.size

    /**
     * Calculates total without discounts
     */
    private fun total(): BigDecimal {
        return _cart.fold(BigDecimal.ZERO) { acc, orderItem ->
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
        //Clear discounts before each calculation
        var totalDiscount = BigDecimal.ZERO
        removeDiscounts()


        for (discount in discounts.distinct()) {
            val amount = discount.apply(_cart)

            val discountableItem = _cart.find { it.item.code == discount.code }
            discountableItem?.applicableDiscount = discount::class.java.simpleName

            if (amount > BigDecimal.ZERO) {
                totalDiscount += amount
                _appliedDiscounts[discount::class.java.simpleName] = amount
                discountableItem?.discount = amount
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
    @Synchronized
    fun add(item: Item) {
        _cart.find { it.item.code == item.code }.apply {
            if (this != null) this.count++
            else _cart.add(OrderItem(item, 1))
        }

        finalTotal()
    }

    /**
     * adds all items
     */
    @Synchronized
    fun addAll(items: List<OrderItem>) {
        clear()
        items.forEach {
            for (i in 1..it.count) {
                add(it.item)
            }
        }
    }

    /**
     * Decrements item's quantity by one & recalculates total
     */
    @Synchronized
    fun subtract(item: Item) {
        _cart.find { it.item.code == item.code }.apply {
            if (this != null) {
                if (this.count > 1) this.count--
                else _cart.remove(this)
            }
        }

        finalTotal()
    }

    /**
     * Gets the given product if exists
     *
     * @param [item] product to be get
     */
    @Synchronized
    fun get(item: Item): OrderItem? {
        return _cart.find { it.item.code == item.code }?.copy()
    }

    /**
     * removes given item from cart & recalculates total
     *
     * @param [item] : item to be removed
     */
    @Synchronized
    fun delete(item: Item) {
        _cart.remove(_cart.find { it.item.code == item.code })
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
    @Synchronized
    fun update(item: Item, qty: Int) {
        if (qty < 0) return

        _cart.find { it.item.code == item.code }
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
    @Synchronized
    fun clear() {
        _cart.clear()
        _appliedDiscounts.clear()
        total = BigDecimal.ZERO
        totalAfterDiscounts = BigDecimal.ZERO
        totalDiscounts = BigDecimal.ZERO
    }

    /**
     *Clear all discount data for each item in the cart
     * and cart applied discounts
     */
    private fun removeDiscounts() {
        _appliedDiscounts.clear()
        _cart.forEach {
            it.discount = null
            it.applicableDiscount = null
        }
    }

    override fun toString(): String {
        return "\nitems: $cart \napplied discounts: $appliedDiscounts \ntotal: $total" +
                " \ntotal discounts: $totalDiscounts \ntotal after discounts: $totalAfterDiscounts"
    }


}