package io.appicenter.data

import io.appicenter.domain.model.Item
import java.math.BigDecimal

object Shared {
    val tshirt = Item(
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = 20.toBigDecimal()
    )
    val voucher = Item(
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = 5.toBigDecimal()
    )

   val mug = Item(
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = BigDecimal(7.5)
    )

}