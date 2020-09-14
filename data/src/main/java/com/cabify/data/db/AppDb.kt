package com.cabify.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cabify.data.cart.db.CartDao
import com.cabify.data.cart.model.DataCart
import com.cabify.data.order.db.OrdersDao
import com.cabify.data.order.model.DataOrder
import com.cabify.data.products.db.ProductsDao
import com.cabify.data.products.model.DataItem

/**
 * App local database including tables
 * cart & products & orders
 */

@Database(
    entities = [DataOrder::class, DataItem::class, DataCart::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun ordersDao(): OrdersDao

    abstract fun productsDao(): ProductsDao

    abstract fun cartDao(): CartDao
}