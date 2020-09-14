package com.cabify.cabifystore.di

import android.content.Context
import androidx.room.Room
import com.cabify.data.cart.db.CartDao
import com.cabify.data.db.AppDb
import com.cabify.data.order.db.OrdersDao
import com.cabify.data.products.db.ProductsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides Data Access Object
 */
@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDb {
        return Room.databaseBuilder(context.applicationContext, AppDb::class.java, "App.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideOrdersDao(db: AppDb): OrdersDao {
        return db.ordersDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDb): CartDao {
        return db.cartDao()
    }

    @Provides
    @Singleton
    fun provideProductsDao(db: AppDb): ProductsDao {
        return db.productsDao()
    }
}