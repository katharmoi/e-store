package com.cabify.domain.utils

import io.reactivex.Observable

interface NetworkUtils {

    fun isConnected(): Boolean

    fun observeNetwork(): Observable<Boolean>
}