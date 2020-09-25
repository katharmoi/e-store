package com.cabify.domain.interactor.cart

import com.cabify.domain.model.PerActivity
import com.cabify.domain.model.ShoppingCart
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable
import javax.inject.Inject

@PerActivity
class SaveCartToDbUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(): Completable {
        return cartRepository.getCart().flatMapCompletable {
            cartRepository.saveCartToDb(it)
        }
    }
}