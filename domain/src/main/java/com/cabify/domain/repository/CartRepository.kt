package com.cabify.domain.repository

import com.cabify.domain.model.Item
import com.cabify.domain.model.OrderItem
import com.cabify.domain.model.ShoppingCart
import io.reactivex.Completable
import io.reactivex.Single

interface CartRepository {

    fun add(item: Item): Completable

    fun get(item: Item): Single<OrderItem>

    fun update(item: Item, qty: Int): Completable

    fun delete(item: Item): Completable

    fun getAll(): Single<List<OrderItem>>

    fun clear(): Completable

    fun saveCartToDb(cart:ShoppingCart):Completable
}