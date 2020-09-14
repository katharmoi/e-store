package com.cabify.domain.interactor.orders

import com.cabify.domain.model.Order
import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.OrdersRepository
import io.reactivex.Completable
import javax.inject.Inject

@PerActivity
class DeleteOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(order: Order): Completable {
        return ordersRepository.delete(order)
    }
}