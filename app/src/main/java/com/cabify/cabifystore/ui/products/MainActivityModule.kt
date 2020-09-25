package com.cabify.cabifystore.ui.products

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.cart.CartFragment
import com.cabify.cabifystore.ui.orders.OrdersFragment
import com.cabify.cabifystore.utils.activity.ActivityUtils
import com.cabify.cabifystore.utils.activity.ActivityUtilsImpl
import com.cabify.cabifystore.utils.router.Router
import com.cabify.cabifystore.utils.router.RouterImpl
import com.cabify.data.order.OrdersRepositoryImpl
import com.cabify.data.order.db.OrdersDao
import com.cabify.data.products.ProductsRepositoryImpl
import com.cabify.data.products.db.ProductsDao
import com.cabify.data.products.service.ProductsAPI
import com.cabify.data.products.service.ProductsService
import com.cabify.data.products.service.ProductsServiceImpl
import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.PerFragment
import com.cabify.domain.repository.OrdersRepository
import com.cabify.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

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


        @PerActivity
        @Provides
        @JvmStatic
        fun provideOrdersRepository(db: OrdersDao): OrdersRepository {
            return OrdersRepositoryImpl(db)
        }

    }
}