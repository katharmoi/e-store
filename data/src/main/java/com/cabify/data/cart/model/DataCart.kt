package com.cabify.data.cart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cabify.domain.model.OrderItem


@Entity(tableName = "cart")
data class DataCart(
    @PrimaryKey val id: Int = 1,
    val items: List<OrderItem>,
    val total: String,
    @ColumnInfo(name = "total_discount") val totalDiscount: String,
    @ColumnInfo(name = "total_after_discounts") val totalAfterDiscount: String
)