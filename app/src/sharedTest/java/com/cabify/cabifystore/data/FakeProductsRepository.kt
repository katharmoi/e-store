package com.cabify.cabifystore.data

import com.cabify.domain.model.Item
import com.cabify.domain.repository.ProductsRepository
import io.reactivex.Completable
import io.reactivex.Flowable


object FakeProductsRepository : ProductsRepository {

    var items: Flowable<List<Item>> = Flowable.empty()

    override fun add(items: List<Item>): Completable {
        return Completable.complete()
    }

    override fun get(): Flowable<List<Item>> {
        return items
    }

}