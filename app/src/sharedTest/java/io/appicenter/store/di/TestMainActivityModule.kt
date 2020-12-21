package io.appicenter.store.di

import android.app.Activity
import androidx.fragment.app.FragmentManager
import io.appicenter.store.R
import io.appicenter.store.ui.products.MainActivity
import io.appicenter.store.ui.products.ProductsFragment
import io.appicenter.store.utils.activity.ActivityUtils
import io.appicenter.store.utils.activity.ActivityUtilsImpl
import io.appicenter.store.utils.router.Router
import io.appicenter.store.utils.router.RouterImpl
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.model.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestMainActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun homeScreenInjector(): ProductsFragment


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