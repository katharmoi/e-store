package com.cabify.domain.interactor.cart

import com.cabify.domain.model.Item
import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.ShoppingCart
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

@PerActivity
class DeleteFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(item: Item): Single<ShoppingCart> {
        return cartRepository.subtract(item)
    }
}