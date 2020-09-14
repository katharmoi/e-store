package com.cabify.domain.interactor.cart

import com.cabify.domain.model.OrderItem
import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.CartRepository
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