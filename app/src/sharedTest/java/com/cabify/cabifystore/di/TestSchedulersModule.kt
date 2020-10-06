package com.cabify.cabifystore.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Named
import javax.inject.Singleton


@Module
class TestSchedulersModule {

    companion object {
        const val MAIN_THREAD_SCHEDULER = "mainScheduler"
        const val BG_SCHEDULAR = "bgScheduler"
    }

    @Singleton
    @Provides
    @Named(MAIN_THREAD_SCHEDULER)
    fun provideMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Singleton
    @Provides
    @Named(BG_SCHEDULAR)
    fun provideBGScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}