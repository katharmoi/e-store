package io.appicenter.data.products.service

import io.appicenter.data.products.adapters.toData
import io.appicenter.data.products.model.DataItem
import io.appicenter.data.utils.transform
import io.reactivex.Single

class ProductsServiceImpl(private val api: ProductsAPI) : ProductsService {
    override fun getAll(): Single<List<DataItem>> {
        return api.getProducts().map { products -> products.products.transform { it.toData() } }
    }
}