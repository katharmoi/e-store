package com.cabify.data.products.service

import com.cabify.data.products.model.DataItem
import io.reactivex.Single

interface ProductsService {
    fun getAll(): Single<List<DataItem>>
}