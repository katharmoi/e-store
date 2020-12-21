package io.appicenter.data.products.db

import androidx.room.*
import io.appicenter.data.products.model.DataItem
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data access object for products table
 */
@Dao
interface ProductsDao {

    /**
     * Insert product into the database. If the product already exists, replace it.
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: DataItem): Completable

    /**
     * Insert product into the database. If the product already exists, replace it.
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(item: List<DataItem>): Completable

    /**
     * Update a given product
     */
    @Update
    fun update(item: DataItem): Completable

    /**
     * Get all products from db
     */
    @Query("SELECT * FROM products")
    fun getAll(): Flowable<List<DataItem>>

    /**
     * Delete all products
     */
    @Query("DELETE FROM products")
    fun deleteAll(): Completable
}