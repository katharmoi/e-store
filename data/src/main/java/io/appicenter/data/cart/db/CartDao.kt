package io.appicenter.data.cart.db

import androidx.room.*
import io.appicenter.data.cart.model.DataCart
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data access object for products table
 */
@Dao
interface CartDao {

    /**
     * Insert cart into the database. If the cart already exists, replace it.
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(cart: DataCart): Completable

    /**
     * Get cart from db
     */
    @Query("SELECT * FROM cart LIMIT 1")
    fun get(): Flowable<DataCart>

    /**
     * Update a given product
     */
    @Update
    fun update(cart: DataCart): Completable

    /**
     * Delete cart
     */
    @Query("DELETE FROM cart")
    fun delete(): Completable
}