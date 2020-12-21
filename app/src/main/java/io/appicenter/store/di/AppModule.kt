package io.appicenter.store.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.appicenter.domain.model.PerActivity
import io.appicenter.store.App
import io.appicenter.store.ui.products.MainActivity
import io.appicenter.store.ui.products.MainActivityModule
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
