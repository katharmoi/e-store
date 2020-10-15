package com.cabify.cabifystore.di

import android.content.Context
import android.net.ConnectivityManager
import com.cabify.cabifystore.BuildConfig
import com.cabify.data.cart.CartRepositoryImpl
import com.cabify.data.cart.db.CartDao
import com.cabify.data.products.ProductsRepositoryImpl
import com.cabify.data.products.db.ProductsDao
import com.cabify.data.products.service.ProductsAPI
import com.cabify.data.products.service.ProductsService
import com.cabify.data.products.service.ProductsServiceImpl
import com.cabify.data.utils.NetworkUtilsImpl
import com.cabify.domain.error.NetworkException
import com.cabify.domain.model.BulkPurchaseDiscount
import com.cabify.domain.model.ShoppingCart
import com.cabify.domain.model.TwoForOneDiscount
import com.cabify.domain.repository.CartRepository
import com.cabify.domain.repository.ProductsRepository
import com.cabify.domain.utils.NetworkUtils
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
import java.math.BigDecimal
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
    fun provideNetworkUtils(connectivityManager: ConnectivityManager): NetworkUtils {
        return NetworkUtilsImpl(connectivityManager)
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ProductsAPI {
        return retrofit.create(ProductsAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideProductsService(api: ProductsAPI): ProductsService {
        return ProductsServiceImpl(api)
    }

    @Singleton
    @Provides
    fun provideProductsRepository(
        service: ProductsService,
        db: ProductsDao
    ): ProductsRepository {
        return ProductsRepositoryImpl(service, db)
    }

    @Singleton
    @Provides
    fun provideCart(): ShoppingCart {
        return ShoppingCart(
            TwoForOneDiscount("VOUCHER"),
            BulkPurchaseDiscount("TSHIRT", BigDecimal.ONE, 3)
        )
    }

    @Singleton
    @Provides
    fun provideCartRepository(
        cart: ShoppingCart,
        cartDao: CartDao
    ): CartRepository {
        return CartRepositoryImpl(cart, cartDao)
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