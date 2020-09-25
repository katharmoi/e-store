package com.cabify.cabifystore.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cabify.cabifystore.di.SchedulersModule
import com.cabify.domain.interactor.cart.*
import com.cabify.domain.interactor.orders.GetOrdersUseCase
import com.cabify.domain.interactor.products.GetProductsUseCase
import com.cabify.domain.model.PerActivity
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
    private val saveCartToDbUseCase: SaveCartToDbUseCase
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
                saveCartToDbUseCase
            ) as T
        } else throw IllegalArgumentException("Unknown View Model Class")
    }
}