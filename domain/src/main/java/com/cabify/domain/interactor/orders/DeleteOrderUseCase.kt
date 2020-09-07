package com.cabify.domain.interactor.orders

import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Completable

class DeleteOrderUseCase(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(orderId: String): Completable {
        return ordersRepository.deleteOrder(orderId)
    }
}