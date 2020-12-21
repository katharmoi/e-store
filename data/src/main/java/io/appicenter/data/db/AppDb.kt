package io.appicenter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.appicenter.data.cart.db.CartDao
import io.appicenter.data.cart.model.DataCart
import io.appicenter.data.order.db.OrdersDao
import io.appicenter.data.order.model.DataOrder
import io.appicenter.data.products.db.ProductsDao
import io.appicenter.data.products.model.DataItem

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