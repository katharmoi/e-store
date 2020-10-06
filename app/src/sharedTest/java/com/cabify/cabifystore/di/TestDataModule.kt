package com.cabify.cabifystore.di

import com.cabify.cabifystore.data.FakeCartRepository
import com.cabify.cabifystore.data.FakeOrdersRepository
import com.cabify.cabifystore.data.FakeProductsRepository
import com.cabify.domain.repository.CartRepository
import com.cabify.domain.repository.OrdersRepository
import com.cabify.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Providers for repository, service and Retrofit objects
 */
@Module
class TestDataModule {

    @Provides
    @Singleton
    fun provideProductsRepo(): ProductsRepository {
        return FakeProductsRepository
    }

    @Provides
    @Singleton
    fun provideCartRepo(): CartRepository {
        return FakeCartRepository
    }

    @Provides
    @Singleton
    fun provideOrdersRepo(): OrdersRepository {
        return FakeOrdersRepository()
    }
}