package io.appicenter.store.ui.products

import android.app.Activity
import androidx.fragment.app.FragmentManager
import io.appicenter.store.R
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.model.PerFragment
import io.appicenter.store.ui.cart.CartFragment
import io.appicenter.store.ui.orders.OrdersFragment
import io.appicenter.store.utils.activity.ActivityUtils
import io.appicenter.store.utils.activity.ActivityUtilsImpl
import io.appicenter.store.utils.router.Router
import io.appicenter.store.utils.router.RouterImpl

@Module
abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun homeScreenInjector(): ProductsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun cartScreenInjector(): CartFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun ordersScreenInjector(): OrdersFragment


    @Binds
    @PerActivity
    abstract fun provideActivity(activity: MainActivity): Activity

    @Binds
    @PerActivity
    abstract fun provideRouter(routerImpl: RouterImpl): Router

    @Binds
    @PerActivity
    abstract fun provideActivityUtils(activityUtilsImpl: ActivityUtilsImpl): ActivityUtils


    @Module
    companion object {

        @PerActivity
        @Provides
        @JvmStatic
        fun provideFragmentManager(activity: MainActivity): FragmentManager {
            return activity.supportFragmentManager
        }

        @PerActivity
        @Provides
        @JvmStatic
        fun provideContainerId(): Int {
            return R.id.main_container
        }

    }
}