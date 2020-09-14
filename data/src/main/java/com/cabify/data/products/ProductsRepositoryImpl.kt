package com.cabify.data.products

import com.cabify.data.products.adapters.toData
import com.cabify.data.products.adapters.toDomain
import com.cabify.data.products.db.ProductsDao
import com.cabify.data.products.service.ProductsService
import com.cabify.data.utils.transform
import com.cabify.domain.model.Item
import com.cabify.domain.repository.ProductsRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class ProductsRepositoryImpl(
    private val productsService: ProductsService,
    private val productsDao: ProductsDao
) : ProductsRepository {
    override fun add(items: List<Item>): Completable {
        return productsDao.addAll(items.transform { item -> item.toData() })
    }

    /**
     *  Uses db as the single source of truth
     */
    override fun get(): Flowable<List<Item>> {
        return productsDao.getAll()
            .mergeWith(productsService.getAll()
                .flatMapCompletable { products -> productsDao.addAll(products) }
            )
            .map { dataItems -> dataItems.transform { it.toDomain() } }
    }
}