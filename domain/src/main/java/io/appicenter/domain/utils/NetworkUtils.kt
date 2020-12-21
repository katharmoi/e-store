package io.appicenter.domain.utils

import io.reactivex.Observable

interface NetworkUtils {

    fun isConnected(): Boolean

    fun observeNetwork(): Observable<Boolean>
}