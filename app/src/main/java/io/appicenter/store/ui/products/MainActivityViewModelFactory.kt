package io.appicenter.store.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.appicenter.domain.interactor.cart.*
import io.appicenter.domain.interactor.orders.GetOrdersUseCase
import io.appicenter.domain.interactor.products.GetProductsUseCase
import io.appicenter.domain.interactor.utils.ObserveNetworkUseCase
import io.appicenter.domain.model.PerActivity
import io.appicenter.store.di.SchedulersModule
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

@PerActivity
class MainActivityViewModelFactory
@Inject constructor(
    @param:Named(SchedulersModule.MAIN_THREAD_SCHEDULER) private val mainScheduler: Scheduler,
    @param:Named(SchedulersModule.BG_SCHEDULAR) private val bgScheduler: Scheduler,
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val deleteFromCartUseCase: DeleteFromCartUseCase,
    private val processOrderUseCase: ProcessOrderUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getCartFromDbUseCase: GetCartFromDbUseCase,
    private val saveCartToDbUseCase: SaveCartToDbUseCase,
    private val observeNetworkUseCase: ObserveNetworkUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                mainScheduler,
                bgScheduler,
                getProductsUseCase,
                addToCartUseCase,
                deleteFromCartUseCase,
                processOrderUseCase,
                getOrdersUseCase,
                getCartFromDbUseCase,
                saveCartToDbUseCase,
                observeNetworkUseCase
            ) as T
        } else throw IllegalArgumentException("Unknown View Model Class")
    }
}