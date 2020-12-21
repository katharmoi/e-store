package io.appicenter.data.order.db

import androidx.room.*
import io.appicenter.data.order.model.DataOrder
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data access object for orders table
 */
@Dao
interface OrdersDao {

    /**
     * Insert an order in the database. If the order already exists, replace it.
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(order: DataOrder): Completable

    /**
     * Update a given order
     */
    @Update
    fun update(order: DataOrder): Completable

    /**
     * Delete a given order
     */
    @Delete
    fun delete(order: DataOrder): Completable

    /**
     * Get all orders from db
     */
    @Query("SELECT * FROM orders")
    fun getAll(): Flowable<List<DataOrder>>

    /**
     * Delete all orders
     */
    @Query("DELETE FROM orders")
    fun deleteAll(): Completable
}