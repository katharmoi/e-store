package com.cabify.domain.interactor.cart

import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable
import javax.inject.Inject

@PerActivity
class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(): Completable {
        return cartRepository.clear()
    }
}