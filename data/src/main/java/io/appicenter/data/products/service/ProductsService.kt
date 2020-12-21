package io.appicenter.data.products.service

import io.appicenter.data.products.model.DataItem
import io.reactivex.Single

interface ProductsService {
    fun getAll(): Single<List<DataItem>>
}