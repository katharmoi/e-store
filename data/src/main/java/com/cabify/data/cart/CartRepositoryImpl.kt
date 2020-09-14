package com.cabify.data.cart

import com.cabify.data.cart.adapters.toData
import com.cabify.data.cart.db.CartDao
import com.cabify.domain.model.Item
import com.cabify.domain.model.OrderItem
import com.cabify.domain.model.ShoppingCart
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.Single

class CartRepositoryImpl(
    private val shoppingCart: ShoppingCart,
    private val cartDao: CartDao
) : CartRepository {
    override fun add(item: Item): Completable {
        return Completable.fromAction { shoppingCart.add(item) }
    }

    override fun get(item: Item): Single<OrderItem> {
        return Single.fromCallable { shoppingCart.get(item) }
    }

    override fun update(item: Item, qty: Int): Completable {
        return Completable.fromAction { shoppingCart.update(item, qty) }
    }

    override fun delete(item: Item): Completable {
        return Completable.fromAction { shoppingCart.delete(item) }
    }

    override fun getAll(): Single<List<OrderItem>> {
        return Single.fromCallable { shoppingCart.cart}
    }

    override fun clear(): Completable {
        return Completable.fromAction { shoppingCart.clear() }
    }

    override fun saveCartToDb(cart: ShoppingCart): Completable {
        return cartDao.add(shoppingCart.toData())
    }
}