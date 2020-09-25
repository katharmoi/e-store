package com.cabify.data.products.service

import com.cabify.data.products.model.Product
import com.cabify.domain.model.Item
import io.reactivex.Single
import retrofit2.http.GET

interface ProductsAPI {

    /**
     * Get products
     */
    @GET("Products.json")
    fun getProducts(): Single<Product>

}