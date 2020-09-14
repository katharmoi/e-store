package com.cabify.data.products.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class DataItem(
    @PrimaryKey val code: String,
    val name: String,
    val price: String
)