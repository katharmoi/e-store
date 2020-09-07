package com.cabify.domain.repository

import com.cabify.domain.model.Order
import io.reactivex.Completable
import io.reactivex.Single

interface OrdersRepository {

    fun addOrder(order: Order): Completable

    fun getOrders(): Single<List<Order>>

    fun deleteOrder(orderId: String): Completable

    fun updateOrder(orderId: String): Completable
}