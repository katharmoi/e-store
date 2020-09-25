package com.cabify.domain.repository

import com.cabify.domain.model.Item
import com.cabify.domain.model.OrderItem
import com.cabify.domain.model.ShoppingCart
import io.reactivex.Completable
import io.reactivex.Single

interface CartRepository {

    fun add(item: Item): Single<ShoppingCart>

    fun get(item: Item): Single<OrderItem>

    fun update(item: Item, qty: Int): Single<ShoppingCart>

    fun subtract(item: Item): Single<ShoppingCart>

    fun delete(item: Item): Single<ShoppingCart>

    fun getAll(): Single<List<OrderItem>>

    fun getCart(): Single<ShoppingCart>

    fun clear(): Single<ShoppingCart>

    fun saveCartToDb(cart: ShoppingCart): Completable

    fun getCartFromDb(): Single<ShoppingCart>
}