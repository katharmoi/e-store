package com.cabify.domain.repository

import com.cabify.domain.model.Item
import io.reactivex.Single

interface ProductsRepository {

    fun getItems(): Single<List<Item>>

}