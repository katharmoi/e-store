package com.cabify.data.products.service

import com.cabify.data.products.adapters.toData
import com.cabify.data.products.model.DataItem
import com.cabify.data.utils.transform
import io.reactivex.Single

class ProductsServiceImpl(private val api: ProductsAPI) : ProductsService {
    override fun getAll(): Single<List<DataItem>> {
        return api.getProducts().map { products -> products.products.transform { it.toData() } }
    }
}