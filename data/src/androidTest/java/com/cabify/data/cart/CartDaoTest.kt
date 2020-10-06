package com.cabify.data.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabify.data.Shared.tshirt
import com.cabify.data.cart.db.CartDao
import com.cabify.data.cart.model.DataCart
import com.cabify.data.db.AppDb
import com.cabify.domain.model.OrderItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal


/**
 * Test the implementation of [CartDao]
 */
@RunWith(AndroidJUnit4::class)
class CartDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cartDao: CartDao
    private lateinit var db: AppDb

    private val cart = DataCart(
        items = listOf(OrderItem(tshirt, 3)),
        total = BigDecimal(100).toString(),
        totalDiscount = BigDecimal.ZERO.toPlainString(),
        totalAfterDiscount = BigDecimal(100).toString()
    )


    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder<AppDb>(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ) // allow main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        cartDao = db.cartDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getCartWhenNoneInserted() {
        cartDao.get()
            .test()
            .assertNoValues()

    }

    @Test
    fun addAndGetCart() {
        //Given we have a cart in the db
        cartDao.add(cart).blockingAwait()


        //when subscribed to the cart emission
        cartDao.get()
            .test()


            //Assert we get the added cart
            .assertValue { res ->
                res.items == listOf(OrderItem(tshirt, 3))
                        && res.total == BigDecimal(100).toString()
                        && res.totalDiscount == BigDecimal.ZERO.toPlainString()
                        && res.totalAfterDiscount == BigDecimal(100).toString()
            }
            .assertValueCount(1)
            .assertNotComplete()
    }

    @Test
    fun updateAndGetCart() {
        //Given we have a cart in the db
        cartDao.add(cart).blockingAwait()

        //Update the total
        val updatedTotal = BigDecimal(110).toString()
        cartDao.update(cart.copy(total = updatedTotal)).blockingAwait()


        //when subscribed to the cart emission
        cartDao.get()
            .test()


            //Assert we get the updated cart
            .assertValue { res ->
                res.items == listOf(OrderItem(tshirt, 3))
                        && res.total == updatedTotal
                        && res.totalDiscount == BigDecimal.ZERO.toPlainString()
                        && res.totalAfterDiscount == BigDecimal(100).toString()
            }
            .assertValueCount(1)
            .assertNotComplete()
    }

    @Test
    fun deleteAndGetCart() {

        //Given we have a cart in the db
        cartDao.add(cart).blockingAwait()

        //When we delete cart
        cartDao.delete().blockingAwait()


        //when subscribed to the cart emission
        cartDao.get()
            .test()
            //Assert no cart exists
            .assertNoValues()

    }

}