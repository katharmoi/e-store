package com.cabify.domain.interactor.orders

import com.cabify.domain.model.Order
import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Completable

class DeleteOrderUseCase(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(order: Order): Completable {
        return ordersRepository.delete(order)
    }
}