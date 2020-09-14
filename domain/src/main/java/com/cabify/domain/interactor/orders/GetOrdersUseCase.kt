package com.cabify.domain.interactor.orders

import com.cabify.domain.model.Order
import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Flowable

class GetOrdersUseCase(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(): Flowable<List<Order>> {
        return ordersRepository.get()
    }
}