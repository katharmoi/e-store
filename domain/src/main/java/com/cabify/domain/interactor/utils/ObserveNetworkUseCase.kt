package com.cabify.domain.interactor.utils

import com.cabify.domain.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.Observable.merge
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Returns an [Observable] that emits network connection state as [Boolean]
 *  Skips initial emissions until it reaches an unconnected message
 */
@Singleton
class ObserveNetworkUseCase @Inject constructor(private val networkUtils: NetworkUtils) {

    operator fun invoke(): Observable<Boolean> {
        return merge(
            Observable.fromCallable { networkUtils.isConnected() },
            networkUtils.observeNetwork().skip(1)
        ).skipWhile { it }
    }
}