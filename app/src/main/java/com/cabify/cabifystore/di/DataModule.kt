package com.cabify.cabifystore.di

import android.content.Context
import android.net.ConnectivityManager
import com.cabify.cabifystore.BuildConfig
import com.cabify.cabifystore.utils.NetworkUtils
import com.cabify.domain.error.NetworkException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Providers for repository, service and Retrofit objects
 */
@Module
class DataModule {

    companion object {
        private const val BASE_URL =
            "https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/"
        private const val CACHE_CONTROL = "cache-control"
        private const val OKHTTP_NETWORK_CACHE = "network-cache"
        private const val OKHTTP_OFFLINE_CACHE_DURATION = "offline-cache"
        private const val OKHTTP_NETWORK_INTERCEPTOR = "network_check"

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        @Named(OKHTTP_NETWORK_INTERCEPTOR) networkInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun provideCache(context: Context): Cache {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http_cache"), (10 * 1024 * 1024).toLong()) //10MB
        } catch (e: Exception) {
            Timber.e("Cannot create cache")
        }

        return cache!!
    }

    @Singleton
    @Provides
    @Named(OKHTTP_NETWORK_CACHE)
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(30, TimeUnit.SECONDS)
                .build()

            response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @Singleton
    @Provides
    @Named(OKHTTP_NETWORK_INTERCEPTOR)
    @Throws(NetworkException::class)
    fun provideNetworkInterceptor(networkUtils: NetworkUtils): Interceptor {

        return Interceptor { chain ->
            if (!networkUtils.isConnected()) {
                throw NetworkException()
            }

            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())

        }
    }

    @Singleton
    @Provides
    @Named(OKHTTP_OFFLINE_CACHE_DURATION)
    fun provideOfflineCacheInterceptor(networkUtils: NetworkUtils): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!networkUtils.isConnected()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRxJavaCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}