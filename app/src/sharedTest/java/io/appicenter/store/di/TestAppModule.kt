package io.appicenter.store.di

import android.content.Context
import io.appicenter.store.ui.cart.CartFragment
import io.appicenter.store.ui.orders.OrdersFragment
import io.appicenter.store.ui.products.MainActivity
import io.appicenter.domain.model.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module
abstract class TestAppModule {

    @Singleton
    @Binds
    abstract fun provideAppContext(app: TestApp): Context

    @PerActivity
    @ContributesAndroidInjector(modules = [TestMainActivityModule::class])
    abstract fun bindMainScreen(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun cartScreenInjector(): CartFragment

    @PerActivity
    @ContributesAndroidInjector
    abstract fun ordersScreenInjector(): OrdersFragment

}
