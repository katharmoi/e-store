package com.cabify.domain.interactor.products

import com.cabify.domain.model.Item
import com.cabify.domain.repository.ProductsRepository
import io.reactivex.Flowable

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
) {

    operator fun invoke(): Flowable<List<Item>> {
        return productsRepository.get()
    }
}