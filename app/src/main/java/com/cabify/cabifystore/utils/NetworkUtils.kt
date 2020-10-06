package com.cabify.cabifystore.utils

import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Network related utility functions
 */
@Singleton
class NetworkUtils @Inject constructor(private val cM: ConnectivityManager) {


    private val isConnectedToNetwork: Boolean
        get() {
            return true
            //return cM.activeNetworkInfo != null && cM.activeNetworkInfo.isConnected
        }

    fun isConnected(): Boolean {
        return isConnectedToNetwork
    }

}