package com.cabify.domain.interactor.cart

import com.cabify.domain.model.Item
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable

class AddToCartUseCase(
    private val cartRepository: CartRepository
) {

    operator fun invoke(item: Item): Completable {
        return cartRepository.add(item)
    }
}