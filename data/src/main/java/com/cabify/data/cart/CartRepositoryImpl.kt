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
    override fun add(item: Item): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.add(item) }
            .map { shoppingCart }
    }

    override fun get(item: Item): Single<OrderItem> {
        return Single.fromCallable { shoppingCart.get(item) }
    }

    override fun update(item: Item, qty: Int): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.update(item, qty) }
            .map { shoppingCart }
    }

    override fun subtract(item: Item): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.subtract(item) }
            .map { shoppingCart }
    }

    override fun delete(item: Item): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.delete(item) }
            .map { shoppingCart }
    }

    override fun getAll(): Single<List<OrderItem>> {
        return Single.fromCallable { shoppingCart.cart }
    }

    override fun getCart(): Single<ShoppingCart> {
        return Single.just(shoppingCart)
    }

    override fun clear(): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.clear() }
            .map { shoppingCart }
    }

    override fun saveCartToDb(cart: ShoppingCart): Completable {
        return cartDao.add(shoppingCart.toData())
    }

    override fun getCartFromDb(): Single<ShoppingCart> {
        return cartDao.get()
            .firstOrError()
            .map { dataCart ->
                shoppingCart.clear()
                dataCart.items.forEach { orderItem ->
                    for (i in 0 until orderItem.count) {
                        shoppingCart.add(orderItem.item)
                    }
                }
                return@map shoppingCart
            }
    }
}