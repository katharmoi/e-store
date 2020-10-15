package com.cabify.data.utils

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import com.cabify.domain.utils.NetworkUtils
import io.reactivex.Observable

class NetworkUtilsImpl(private val connectivityManager: ConnectivityManager) : NetworkUtils {

    override fun isConnected(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NET_CAPABILITY_INTERNET) ?: false
    }

    override fun observeNetwork(): Observable<Boolean> {

        return Observable.create { emitter ->

            val request = NetworkRequest.Builder().build()

            val callback = object : NetworkCallback() {

                override fun onAvailable(network: Network) {
                    emitter.onNext(true)
                }

                override fun onLost(network: Network) {
                    emitter.onNext(false)
                }
            }

            connectivityManager.registerNetworkCallback(request, callback)
            emitter.setCancellable { connectivityManager.unregisterNetworkCallback(callback) }
        }

    }

}