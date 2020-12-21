package io.appicenter.domain.interactor.orders

import io.appicenter.domain.model.Order
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.repository.OrdersRepository
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