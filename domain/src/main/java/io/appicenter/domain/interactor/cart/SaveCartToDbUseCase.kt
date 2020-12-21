package io.appicenter.domain.interactor.cart

import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.repository.CartRepository
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