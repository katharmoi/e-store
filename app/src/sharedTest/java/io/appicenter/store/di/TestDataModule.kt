package io.appicenter.store.di

import io.appicenter.store.data.FakeCartRepository
import io.appicenter.store.data.FakeNetworkUtils
import io.appicenter.store.data.FakeOrdersRepository
import io.appicenter.store.data.FakeProductsRepository
import io.appicenter.domain.repository.CartRepository
import io.appicenter.domain.repository.OrdersRepository
import io.appicenter.domain.repository.ProductsRepository
import io.appicenter.domain.utils.NetworkUtils
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
        return FakeOrdersRepository
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils {
        return FakeNetworkUtils
    }
}