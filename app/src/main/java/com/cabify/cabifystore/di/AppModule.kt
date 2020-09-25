package com.cabify.cabifystore.di

import android.content.Context
import com.cabify.cabifystore.App
import com.cabify.cabifystore.ui.products.MainActivity
import com.cabify.cabifystore.ui.products.MainActivityModule
import com.cabify.domain.model.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideAppContext(app: App): Context

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainScreen(): MainActivity

}
