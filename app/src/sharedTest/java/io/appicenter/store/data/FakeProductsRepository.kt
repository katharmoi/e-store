package io.appicenter.store.data

import io.appicenter.domain.model.Item
import io.appicenter.domain.repository.ProductsRepository
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