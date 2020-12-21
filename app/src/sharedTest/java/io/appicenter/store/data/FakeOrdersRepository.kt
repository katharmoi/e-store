package io.appicenter.store.data

import io.appicenter.domain.model.Order
import io.appicenter.domain.repository.OrdersRepository
import io.reactivex.Completable
import io.reactivex.Flowable

object FakeOrdersRepository : OrdersRepository {

    var orders: List<Order> = listOf()

    override fun add(order: Order): Completable {
        return Completable.complete()
    }

    override fun get(): Flowable<List<Order>> {
        return Flowable.just(orders)
    }

    override fun delete(order: Order): Completable {
        return Completable.complete()
    }

    override fun update(order: Order): Completable {
        return Completable.complete()
    }

    override fun deleteAll(): Completable {
        return Completable.complete()
    }
}