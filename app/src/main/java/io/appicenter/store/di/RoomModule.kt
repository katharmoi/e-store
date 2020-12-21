package io.appicenter.store.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.appicenter.data.cart.db.CartDao
import io.appicenter.data.db.AppDb
import io.appicenter.data.order.db.OrdersDao
import io.appicenter.data.products.db.ProductsDao
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