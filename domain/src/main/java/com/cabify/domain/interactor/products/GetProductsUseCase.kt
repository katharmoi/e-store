package com.cabify.domain.interactor.products

import com.cabify.domain.model.Item
import com.cabify.domain.model.PerActivity
import com.cabify.domain.repository.ProductsRepository
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