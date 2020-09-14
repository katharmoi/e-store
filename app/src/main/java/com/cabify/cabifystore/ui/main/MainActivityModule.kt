package com.cabify.cabifystore.ui.main

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.cabify.cabifystore.R
import com.cabify.cabifystore.utils.router.Router
import com.cabify.cabifystore.utils.router.RouterImpl
import com.cabify.data.order.OrdersRepositoryImpl
import com.cabify.data.order.db.OrdersDao
import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.PerFragment
import com.cabify.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun homeScreenInjector(): HomeFragment

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