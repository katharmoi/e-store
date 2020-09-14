package com.cabify.domain.interactor.orders

import com.cabify.domain.model.Order
import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Flowable
import javax.inject.Inject

@PerActivity
class GetOrdersUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(): Flowable<List<Order>> {
        return ordersRepository.get()
    }
}