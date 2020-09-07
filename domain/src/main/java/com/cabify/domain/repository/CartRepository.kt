package com.cabify.domain.repository

import com.cabify.domain.model.OrderItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface CartRepository {

    fun add(item: OrderItem): Completable

    fun get(): Flowable<List<OrderItem>>

    fun delete(item: OrderItem): Completable

    fun update(item: OrderItem): Completable

    fun clean(): Completable
}