package com.cabify.cabifystore.utils

import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Network related utility functions
 */
@Singleton
class NetworkUtils @Inject constructor(private val connectivityManager: ConnectivityManager): ConnectivityManager.NetworkCallback() {

    private val isConnectedToNetwork: Boolean
        get() {
            return connectivityManager.activeNetworkInfo != null
        }

    fun isConnected(): Boolean {
        return isConnectedToNetwork
    }

}