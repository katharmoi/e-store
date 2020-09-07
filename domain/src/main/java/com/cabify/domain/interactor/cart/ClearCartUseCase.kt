package com.cabify.domain.interactor.cart

import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable

class ClearCartUseCase(
    private val cartRepository: CartRepository
) {

    operator fun invoke(): Completable {
        return cartRepository.clear()
    }
}