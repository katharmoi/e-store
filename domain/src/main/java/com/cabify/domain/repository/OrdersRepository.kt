package com.cabify.domain.repository

import com.cabify.domain.model.Order
import io.reactivex.Completable
import io.reactivex.Flowable

interface OrdersRepository {

    fun add(order: Order): Completable

    fun get(): Flowable<List<Order>>

    fun delete(order: Order): Completable

    fun update(order: Order): Completable

    fun deleteAll(): Completable
}