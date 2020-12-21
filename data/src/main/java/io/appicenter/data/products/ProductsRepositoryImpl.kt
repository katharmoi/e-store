package io.appicenter.data.products

import io.appicenter.data.products.adapters.toData
import io.appicenter.data.products.adapters.toDomain
import io.appicenter.data.products.db.ProductsDao
import io.appicenter.data.products.model.DataItem
import io.appicenter.data.products.service.ProductsService
import io.appicenter.data.utils.transform
import io.appicenter.domain.model.Item
import io.appicenter.domain.repository.ProductsRepository
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