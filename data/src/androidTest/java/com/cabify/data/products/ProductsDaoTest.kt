package com.cabify.data.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabify.data.Shared.mug
import com.cabify.data.Shared.tshirt
import com.cabify.data.db.AppDb
import com.cabify.data.products.adapters.toData
import com.cabify.data.products.db.ProductsDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test the implementation of [ProductsDao]
 */
@RunWith(AndroidJUnit4::class)
class ProductsDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productsDao: ProductsDao
    private lateinit var db: AppDb


    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder<AppDb>(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ) // allow main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        productsDao = db.productsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getProductsWhenNoneInserted() {
        productsDao.getAll()
            .test()
            .assertValue { res -> res.isEmpty() }

    }

    @Test
    fun addAndGetProduct() {
        //Given we have a product in the db
        productsDao.add(tshirt.toData()).blockingAwait()
        productsDao.add(mug.toData()).blockingAwait()


        //when subscribed to the product emission
        productsDao.getAll()
            .test()
            //Assert we get only the added products
            .assertValue { res -> res.size == 2 }
            .assertValue { res ->
                res[0].code == tshirt.code
            }
            .assertValue { res ->
                res[1].code == mug.code
            }
            .assertValueCount(1)
            .assertNotComplete()
    }

    @Test
    fun updateAndGetProduct() {
        //Given we have a product in the db
        productsDao.add(tshirt.toData()).blockingAwait()

        //Update the total
        val updatedName = "Cabify Updated Tshirt"
        productsDao.update(tshirt.copy(name = updatedName).toData()).blockingAwait()


        //when subscribed to the product emission
        productsDao.getAll()
            .test()
            //Assert we get only the added product
            .assertValue { res -> res.size == 1 }
            //Assert total is updated
            .assertValue { res ->
                res[0].name == updatedName
            }

    }

    @Test
    fun deleteAndGetProduct() {

        //Given we have a product in the db
        productsDao.add(tshirt.toData()).blockingAwait()

        //When we delete all products
        productsDao.deleteAll().blockingAwait()


        //when subscribed to the product emission
        productsDao.getAll()
            .test()
            //Assert no products exists
            .assertValue { res -> res.isEmpty() }

    }

}