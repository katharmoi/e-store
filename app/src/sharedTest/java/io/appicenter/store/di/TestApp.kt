package io.appicenter.store.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class TestApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (io.appicenter.store.BuildConfig.DEBUG)
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
    }

    override fun applicationInjector(): AndroidInjector<out TestApp> {
        return DaggerTestAppComponent.builder().create(this)
    }
}