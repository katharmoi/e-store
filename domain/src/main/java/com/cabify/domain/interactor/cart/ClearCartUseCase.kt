package com.cabify.domain.interactor.cart

import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.ShoppingCart
import com.cabify.domain.repository.CartRepository
import io.reactivex.Single
import javax.inject.Inject

@PerActivity
class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(): Single<ShoppingCart> {
        return cartRepository.clear()
    }
}