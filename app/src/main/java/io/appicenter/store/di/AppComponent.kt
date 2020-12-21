package io.appicenter.store.di

import androidx.annotation.VisibleForTesting

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.appicenter.domain.repository.CartRepository
import io.appicenter.domain.repository.OrdersRepository
import io.appicenter.domain.repository.ProductsRepository
import io.appicenter.store.App
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DataModule::class,
        RoomModule::class,
        SchedulersModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    @VisibleForTesting
    fun getOkHttpClient(): OkHttpClient

    @VisibleForTesting
    fun getCartRepository(): CartRepository

    @VisibleForTesting
    fun getProductsRepository(): ProductsRepository

    @VisibleForTesting
    fun getOrdersRepository(): OrdersRepository
}




