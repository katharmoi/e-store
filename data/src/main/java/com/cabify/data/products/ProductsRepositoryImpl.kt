package com.cabify.data.products

import com.cabify.data.products.adapters.toData
import com.cabify.data.products.adapters.toDomain
import com.cabify.data.products.db.ProductsDao
import com.cabify.data.products.model.DataItem
import com.cabify.data.products.service.ProductsService
import com.cabify.data.utils.transform
import com.cabify.domain.model.Item
import com.cabify.domain.repository.ProductsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ProductsRepositoryImpl(
    private val productsService: ProductsService,
    private val productsDao: ProductsDao
) : ProductsRepository {
    override fun add(items: List<Item>): Completable {
        return productsDao.addAll(items.transform { item -> item.toData() })
    }

    /**
     *  Uses db as the single source of truth
     *  Db and network calls made concurrently to increase
     *  response time
     */
    override fun get(): Flowable<List<Item>> {
        return Flowable.mergeDelayError(
            productsDao.getAll(),
            productsService.getAll().subscribeOn(Schedulers.io())
                .flatMapCompletable { products -> productsDao.addAll(products) }
                .toFlowable<List<DataItem>>()
                .subscribeOn(Schedulers.io())
        ).map { dataItems -> dataItems.transform { it.toDomain() } }
    }
}