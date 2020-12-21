package io.appicenter.domain.interactor.products

import io.appicenter.domain.model.Item
import io.appicenter.domain.model.PerActivity
import io.appicenter.domain.repository.ProductsRepository
import io.reactivex.Flowable
import javax.inject.Inject

@PerActivity
class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    operator fun invoke(): Flowable<List<Item>> {
        return productsRepository.get()
    }
}