package com.cabify.data.cart.adapters

import com.cabify.data.cart.model.DataCart
import com.cabify.domain.model.ShoppingCart

fun ShoppingCart.toData(): DataCart {
    return DataCart(
        items = this.cart,
        appliedDiscounts = this.appliedDiscounts,
        total = this.total.toPlainString(),
        totalDiscount = this.totalDiscounts.toPlainString(),
        totalAfterDiscount = this.totalAfterDiscounts.toPlainString()
    )

}