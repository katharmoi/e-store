package io.appicenter.data.order.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.appicenter.domain.model.OrderItem
import java.util.*


@Entity(tableName = "orders")
data class DataOrder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val items: List<OrderItem>,
    @Embedded val customer: DataCustomer,
    val total: String,
    val date: Date?
)