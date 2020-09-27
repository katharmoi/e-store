package com.cabify.cabifystore.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.cabifystore.ui.base.BaseViewModel
import com.cabify.cabifystore.utils.Response
import com.cabify.cabifystore.utils.SingleLiveEvent
import com.cabify.domain.interactor.cart.*
import com.cabify.domain.interactor.orders.GetOrdersUseCase
import com.cabify.domain.interactor.products.GetProductsUseCase
import com.cabify.domain.model.Item
import com.cabify.domain.model.Order
import com.cabify.domain.model.ShoppingCart
import io.reactivex.Scheduler
import timber.log.Timber

class MainActivityViewModel(
    private val mainScheduler: Scheduler,
    private val bgScheduler: Scheduler,
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val deleteFromCartUseCase: DeleteFromCartUseCase,
    private val processOrderUseCase: ProcessOrderUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getCartFromDbUseCase: GetCartFromDbUseCase,
    private val saveCartToDbUseCase: SaveCartToDbUseCase
) : BaseViewModel() {

    init {
        getItems()
    }

    enum class ScreenStates {
        HOME, CART, ORDERS
    }

    var currentState = ScreenStates.HOME

    private val _items = MutableLiveData<Response<List<Item>>>()
    val items: LiveData<Response<List<Item>>> = _items

    private val _cart = MutableLiveData<Response<ShoppingCart>>()
    val cart: LiveData<Response<ShoppingCart>> = _cart

    private val _payment = SingleLiveEvent<Response<ShoppingCart>>()
    val payment: LiveData<Response<ShoppingCart>> = _payment

    private val _orders = MutableLiveData<Response<List<Order>>>()
    val orders: LiveData<Response<List<Order>>> = _orders

    fun getItems() {
        disposables.add(
            getProductsUseCase()
                .doOnSubscribe { _items.postValue(Response.Loading) }
                .observeOn(mainScheduler, true)
                .subscribeOn(bgScheduler)
                .subscribe(
                    {
                        _items.value = Response.Success(it)
                        //Load cached cart data if available
                        getCartFromDb()
                    },
                    {
                        Timber.e(it)
                        _items.value = Response.Error(it)
                    }
                )
        )
    }

    fun addToCart(item: Item) {
        disposables.add(
            addToCartUseCase(item)
                .doOnSubscribe { _cart.postValue(Response.Loading) }
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    { _cart.value = Response.Success(it) },
                    {
                        Timber.e(it)
                        _cart.value = Response.Error(it)
                    }
                )
        )

    }

    fun removeFromCart(item: Item) {
        disposables.add(
            deleteFromCartUseCase(item)
                .doOnSubscribe { _cart.postValue(Response.Loading) }
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    { _cart.value = Response.Success(it) },
                    {
                        Timber.e(it)
                        _cart.value = Response.Error(it)
                    }
                )
        )
    }

    fun finishOrder() {
        disposables.add(
            processOrderUseCase()
                .doOnSubscribe { _payment.postValue(Response.Loading) }
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    {
                        _cart.value = Response.Success(it)
                        _payment.value = Response.Success(it)
                    },
                    {
                        Timber.e(it)
                        _payment.value = Response.Error(it)
                    }
                )
        )

    }

    fun getOrders() {
        disposables.add(
            getOrdersUseCase()
                .doOnSubscribe { _orders.postValue(Response.Loading) }
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    { _orders.value = Response.Success(it) },
                    {
                        Timber.e(it)
                        _orders.value = Response.Error(it)
                    }
                )
        )
    }

    fun getCartFromDb() {
        disposables.add(
            getCartFromDbUseCase()
                .doOnSubscribe { _cart.postValue(Response.Loading) }
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    { _cart.value = Response.Success(it) },
                    {
                        Timber.e(it)
                        _cart.value = Response.Error(it)
                    }
                )
        )
    }

    fun saveCartToDb() {
        disposables.add(
            saveCartToDbUseCase()
                .observeOn(mainScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(
                    { },
                    { Timber.e(it) }
                )
        )
    }

}