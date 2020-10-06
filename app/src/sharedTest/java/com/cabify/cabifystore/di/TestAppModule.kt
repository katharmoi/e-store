package com.cabify.cabifystore.di

import android.content.Context
import com.cabify.cabifystore.ui.products.MainActivity
import com.cabify.cabifystore.ui.products.ProductsFragment
import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.PerFragment
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

}
