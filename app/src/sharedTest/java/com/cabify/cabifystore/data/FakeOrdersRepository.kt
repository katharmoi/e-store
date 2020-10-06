package com.cabify.cabifystore.data

import com.cabify.domain.model.Order
import com.cabify.domain.repository.OrdersRepository
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