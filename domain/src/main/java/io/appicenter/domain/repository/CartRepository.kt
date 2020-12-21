package io.appicenter.domain.repository

import io.appicenter.domain.model.Item
import io.appicenter.domain.model.OrderItem
import io.appicenter.domain.model.ShoppingCart
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