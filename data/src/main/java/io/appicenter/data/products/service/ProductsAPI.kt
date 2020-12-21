package io.appicenter.data.products.service

import io.appicenter.data.products.model.Product
import io.reactivex.Single
import retrofit2.http.GET

interface ProductsAPI {

    /**
     * Get products
     */
    @GET("Products.json")
    fun getProducts(): Single<Product>

}