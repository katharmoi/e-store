package io.appicenter.data.cart.adapters

import io.appicenter.data.cart.model.DataCart
import io.appicenter.domain.model.ShoppingCart

fun ShoppingCart.toData(): DataCart {
    return DataCart(
        items = this.cart,
        total = this.total.toPlainString(),
        totalDiscount = this.totalDiscounts.toPlainString(),
        totalAfterDiscount = this.totalAfterDiscounts.toPlainString()
    )

}