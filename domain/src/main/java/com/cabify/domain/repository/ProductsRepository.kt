package com.cabify.domain.repository

import com.cabify.domain.model.Item
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ProductsRepository {

    fun add(items: List<Item>): Completable

    fun get(): Flowable<List<Item>>

}