package com.cabify.cabifystore

import com.cabify.domain.model.*
import java.math.BigDecimal
import java.util.*

object SharedTestData {
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

    val shoppingCart = ShoppingCart().apply {
        add(tshirt)
        add(mug)
        add(voucher)
    }

    val orders = listOf(
        Order(
            items = listOf(
                OrderItem(tshirt, 3),
                OrderItem(voucher, 5),
                OrderItem(mug, 1)
            ),
            customer = Customer(
                "some", "name", "asd@asd.com",
                Address(
                    "some address", "add2", "izmir", "35050",
                    "Turkey", "0000"
                ), "tr"
            ),
            total = 100.50.toBigDecimal(),
            date = Date()
        )
    )

}