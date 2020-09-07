package com.cabify.domain.interactor.orders

import com.cabify.domain.model.Order
import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Completable
import io.reactivex.Single

class GetOrdersUseCase(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(): Single<List<Order>> {
        return ordersRepository.getOrders()
    }
}