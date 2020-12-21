package io.appicenter.store.data

import io.appicenter.domain.utils.NetworkUtils
import io.reactivex.Observable

object FakeNetworkUtils:NetworkUtils {

    var connected = true

    override fun isConnected(): Boolean {
        return connected
    }

    override fun observeNetwork(): Observable<Boolean> {
        return Observable.just(connected)
    }
}