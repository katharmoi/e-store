package com.cabify.data.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabify.data.Shared.mug
import com.cabify.data.Shared.tshirt
import com.cabify.data.Shared.voucher
import com.cabify.data.db.AppDb
import com.cabify.data.order.adapters.toData
import com.cabify.data.order.db.OrdersDao
import com.cabify.domain.model.Address
import com.cabify.domain.model.Customer
import com.cabify.domain.model.Order
import com.cabify.domain.model.OrderItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


/**
 * Test the implementation of [OrdersDao]
 */
@RunWith(AndroidJUnit4::class)
class OrdersDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var ordersDao: OrdersDao
    private lateinit var db: AppDb


    private val order = Order(
        items = listOf(OrderItem(tshirt, 3), OrderItem(voucher, 5), OrderItem(mug, 1)),
        customer = Customer(
            "some", "name", "asd@asd.com",
            Address(
                "some address", "add2", "izmir", "35050",
                "Turkey", "0000"
            ), "tr"
        ),
        total = 100.50.toBigDecimal(),
        date = Date()
    )

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder<AppDb>(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ) // allow main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        ordersDao = db.ordersDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getOrdersWhenNoneInserted() {
        ordersDao.getAll()
            .test()
            .assertValue { res -> res.isEmpty() }

    }

    @Test
    fun addAndGetOrder() {
        //Given we have an order in the db
        ordersDao.add(order.toData()).blockingAwait()
        ordersDao.add(order.toData()).blockingAwait()


        //when subscribed to the order emission
        ordersDao.getAll()
            .test()
            //Assert we get only the added order
            .assertValue { res -> res.size == 2 }
            .assertValue { res ->
                res[0].id == 1 && res[0].items == order.items
                        && res[0].customer == order.customer.toData()
            }
            .assertValue { res ->
                res[1].id == 2 && res[1].items == order.items
                        && res[1].customer == order.customer.toData()
            }
            .assertValueCount(1)
            .assertNotComplete()
    }

    @Test
    fun updateAndGetOrder() {
        //Given we have an order in the db
        ordersDao.add(order.toData()).blockingAwait()

        //Update the total
        val updatedTotal = 13.toBigDecimal()
        ordersDao.update(order.copy(id = 1, total = updatedTotal).toData()).blockingAwait()


        //when subscribed to the order emission
        ordersDao.getAll()
            .test()
            //Assert we get only the added order
            .assertValue { res -> res.size == 1 }
            //Assert total is updated
            .assertValue { res ->
                res[0].total == updatedTotal.toString()
            }

    }

    @Test
    fun deleteAndGetOrder() {

        //Given we have an order in the db
        ordersDao.add(order.toData()).blockingAwait()

        //When we delete all orders
        ordersDao.deleteAll().blockingAwait()


        //when subscribed to the order emission
        ordersDao.getAll()
            .test()
            //Assert no order exists
            .assertValue { res -> res.isEmpty() }

    }

}