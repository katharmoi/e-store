package io.appicenter.domain.interactor.orders

import io.appicenter.domain.model.Order
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.repository.OrdersRepository
import io.reactivex.Completable
import javax.inject.Inject

@PerActivity
class AddOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {

    operator fun invoke(order: Order): Completable {
        return ordersRepository.add(order)
    }
}