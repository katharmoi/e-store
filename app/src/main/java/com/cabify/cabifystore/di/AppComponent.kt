package com.cabify.cabifystore.di

import com.cabify.cabifystore.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
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
    fun getOkHttpClient(): OkHttpClient
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(app:App):Builder

        fun build():AppComponent
    }
}




