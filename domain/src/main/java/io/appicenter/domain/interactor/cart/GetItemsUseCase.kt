package io.appicenter.domain.interactor.cart

import io.appicenter.domain.model.OrderItem
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.repository.CartRepository
import io.reactivex.Single
import javax.inject.Inject

@PerActivity
class GetItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(): Single<List<OrderItem>> {
        return cartRepository.getAll()
    }
}