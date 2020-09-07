package com.cabify.domain.interactor.products

import com.cabify.domain.model.Item
import com.cabify.domain.repository.ProductsRepository
import io.reactivex.Single

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
) {

    operator fun invoke(): Single<List<Item>> {
        return productsRepository.getItems()
    }
}