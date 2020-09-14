package com.cabify.domain.interactor.cart

import com.cabify.domain.model.Item
import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.CartRepository
import io.reactivex.Completable
import javax.inject.Inject

@PerActivity
class UpdateCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(item: Item, qty: Int): Completable {
        return cartRepository.update(item, qty)
    }
}