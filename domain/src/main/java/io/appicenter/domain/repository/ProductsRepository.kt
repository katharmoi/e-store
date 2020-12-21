package io.appicenter.domain.repository

import io.appicenter.domain.model.Item
import io.reactivex.Completable
import io.reactivex.Flowable

interface ProductsRepository {

    fun add(items: List<Item>): Completable

    fun get(): Flowable<List<Item>>

}