package com.cabify.domain.repository

import com.cabify.domain.model.Order
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface OrdersRepository {

    fun addOrder(order: Order): Completable

    fun getOrders(): Flowable<List<Order>>

    fun deleteOrder(id: String): Single<String>

    fun updateOrder(id: String): Single<String>
}