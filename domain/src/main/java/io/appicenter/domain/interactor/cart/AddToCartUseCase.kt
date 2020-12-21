package io.appicenter.domain.interactor.cart

import io.appicenter.domain.model.Item
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.domain.repository.CartRepository
import io.reactivex.Single
import javax.inject.Inject

@PerActivity
class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    operator fun invoke(item: Item): Single<ShoppingCart> {
        return cartRepository.add(item)
    }
}