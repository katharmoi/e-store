package io.appicenter.data.cart

import io.appicenter.data.cart.adapters.toData
import io.appicenter.data.cart.db.CartDao
import io.appicenter.data.utils.transform
import io.appicenter.domain.model.Item
import io.appicenter.domain.model.OrderItem
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.domain.repository.CartRepository
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
        return Single.fromCallable { shoppingCart }
    }

    override fun clear(): Single<ShoppingCart> {
        return Single.fromCallable { shoppingCart.clear() }
            .flatMapCompletable { cartDao.delete() }
            .andThen(Single.fromCallable { shoppingCart })
    }

    override fun saveCartToDb(cart: ShoppingCart): Completable {
        return cartDao.add(shoppingCart.toData())
    }

    override fun getCartFromDb(): Single<ShoppingCart> {
        return Single.zip(
            cartDao.get()
                .firstOrError()
                .map { dataCart ->
                    dataCart.items.transform { OrderItem(it.item, it.count) }
                },
            Single.fromCallable { shoppingCart },
            { items, cart ->
                cart.addAll(items)
                return@zip cart
            }
        )
    }
}