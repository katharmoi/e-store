package io.appicenter.store.data

import io.appicenter.domain.model.Item
import io.appicenter.domain.model.OrderItem
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.domain.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.Single

object FakeCartRepository : CartRepository {
    var shoppingCart = ShoppingCart()

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
        return Completable.complete()
    }

    override fun getCartFromDb(): Single<ShoppingCart> {
        return Single.just(shoppingCart)
    }
}