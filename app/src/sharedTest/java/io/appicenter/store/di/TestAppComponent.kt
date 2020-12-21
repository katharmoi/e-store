package io.appicenter.store.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
        TestSchedulersModule::class,
        TestDataModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestApp>()
}
