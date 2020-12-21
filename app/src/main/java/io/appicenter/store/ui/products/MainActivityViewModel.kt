package io.appicenter.store.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.appicenter.domain.interactor.cart.*
import io.appicenter.domain.interactor.orders.GetOrdersUseCase
import io.appicenter.domain.interactor.products.GetProductsUseCase
import io.appicenter.domain.interactor.utils.ObserveNetworkUseCase
import io.appicenter.domain.model.Item
import io.appicenter.domain.model.Order
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.store.ui.base.BaseViewModel
import io.appicenter.store.utils.Response
import io.appicenter.store.utils.SingleLiveEvent
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
    private val saveCartToDbUseCase: SaveCartToDbUseCase,
    private val observerNetworkUseCase: ObserveNetworkUseCase
) : BaseViewModel() {

    init {
        observeNetwork()
        getItems()
    }

    enum class ScreenStates {
        HOME, CART, ORDERS
    }

    var currentState = ScreenStates.HOME

    private val _networkStatus = SingleLiveEvent<Boolean>()
    val networkStatus: LiveData<Boolean> = _networkStatus

    private val _items = MutableLiveData<Response<List<Item>>>()
    val items: LiveData<Response<List<Item>>> = _items

    private val _cart = MutableLiveData<Response<ShoppingCart>>()
    val cart: LiveData<Response<ShoppingCart>> = _cart

    private val _payment = SingleLiveEvent<Response<ShoppingCart>>()
    val payment: LiveData<Response<ShoppingCart>> = _payment

    private val _orders = MutableLiveData<Response<List<Order>>>()
    val orders: LiveData<Response<List<Order>>> = _orders

    private fun observeNetwork() {
        disposables.add(
            observerNetworkUseCase()
                .observeOn(mainScheduler)
                .subscribe(
                    { _networkStatus.value = it },
                    { Timber.e(it) })
        )
    }

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