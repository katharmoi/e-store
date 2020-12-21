package io.appicenter.store.ui.products

import com.google.common.truth.Truth
import io.appicenter.domain.interactor.cart.*
import io.appicenter.domain.interactor.orders.GetOrdersUseCase
import io.appicenter.domain.interactor.products.GetProductsUseCase
import io.appicenter.domain.interactor.utils.ObserveNetworkUseCase
import io.appicenter.store.InstantExecutorExtension
import io.appicenter.store.TestData.mug
import io.appicenter.store.TestData.orders
import io.appicenter.store.TestData.shoppingCart
import io.appicenter.store.TestData.tshirt
import io.appicenter.store.testObserver
import io.appicenter.store.utils.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(value = [InstantExecutorExtension::class])
internal class MainActivityViewModelTest {

    lateinit var underTest: MainActivityViewModel

    lateinit var testScheduler: TestScheduler

    //Mock use cases
    val getProductsUseCase: GetProductsUseCase = mockk()
    val addToCartUseCase: AddToCartUseCase = mockk()
    val deleteFromCartUseCase: DeleteFromCartUseCase = mockk()
    val processOrderUseCase: ProcessOrderUseCase = mockk()
    val getOrderUseCase: GetOrdersUseCase = mockk()
    val getCartFromDbUseCase: GetCartFromDbUseCase = mockk()
    val saveCartToDbUseCase: SaveCartToDbUseCase = mockk()

    private val observeNetworkUseCase: ObserveNetworkUseCase = mockk()
    val error = RuntimeException("Test Error")

    @BeforeEach
    fun setUp() {
        testScheduler = TestScheduler()
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }

        every {
            getProductsUseCase()
        } returns Flowable.empty()

        every {
            observeNetworkUseCase.invoke()
        } returns Observable.just(true)

        underTest = MainActivityViewModel(
            testScheduler,
            testScheduler,
            getProductsUseCase,
            addToCartUseCase,
            deleteFromCartUseCase,
            processOrderUseCase,
            getOrderUseCase,
            getCartFromDbUseCase,
            saveCartToDbUseCase,
            observeNetworkUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }


    @Nested
    @DisplayName("Given get items fail ")
    inner class GetItemsFail {
        @Test
        fun `Then it should pass error response `() {
            every {
                getProductsUseCase()
            } returns Flowable.error(error)

            val itemsLiveData = underTest.items.testObserver()

            underTest.getItems()

            testScheduler.triggerActions()

            verify(exactly = 2) {
                getProductsUseCase()
            }

            Truth.assert_()
                .that(itemsLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(itemsLiveData.observedValues[2])
                .isEqualTo(Response.Error(error))
        }
    }

    @Nested
    @DisplayName("Given get items succeeds ")
    inner class GetItemsSuccessful {
        @Test
        fun `Then it should return items and call getCart `() {
            val items = listOf(tshirt, mug)
            every {
                getProductsUseCase()
            } returns Flowable.just(items)

            val itemsLiveData = underTest.items.testObserver()

            underTest.getItems()

            testScheduler.triggerActions()

            verify(exactly = 2) {
                getProductsUseCase()
            }

            verify(exactly = 1) {
                getCartFromDbUseCase()
            }


            Truth.assert_()
                .that(itemsLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(itemsLiveData.observedValues[2])
                .isEqualTo(Response.Success(items))
        }
    }

    @Nested
    @DisplayName("Given add to cart fails ")
    inner class AddToCartFails {
        @Test
        fun `Then it should pass error response `() {

            every {
                addToCartUseCase(any())
            } returns Single.error(error)

            val cartLiveData = underTest.cart.testObserver()

            underTest.addToCart(mug)

            testScheduler.triggerActions()

            verify(exactly = 1) {
                addToCartUseCase(any())
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Error(error))

        }
    }

    @Nested
    @DisplayName("Given add to cart succeeds ")
    inner class AddToCartSucceeds {
        @Test
        fun `Then it should return items in the cart `() {

            every {
                addToCartUseCase(any())
            } returns Single.just(shoppingCart)

            val cartLiveData = underTest.cart.testObserver()

            underTest.addToCart(mug)

            testScheduler.triggerActions()

            verify(exactly = 1) {
                addToCartUseCase(any())
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Success(shoppingCart))

        }
    }

    @Nested
    @DisplayName("Given remove from cart fails ")
    inner class RemoveFromCartFails {
        @Test
        fun `Then it should pass error response `() {
            every {
                deleteFromCartUseCase(any())
            } returns Single.error(error)

            val cartLiveData = underTest.cart.testObserver()

            underTest.removeFromCart(mug)

            testScheduler.triggerActions()

            verify(exactly = 1) {
                deleteFromCartUseCase(any())
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Error(error))

        }
    }

    @Nested
    @DisplayName("Given remove from cart succeeds ")
    inner class RemoveFromCartSucceeds {
        @Test
        fun `Then it should return items in the cart `() {

            every {
                deleteFromCartUseCase(any())
            } returns Single.just(shoppingCart)

            val cartLiveData = underTest.cart.testObserver()

            underTest.removeFromCart(mug)

            testScheduler.triggerActions()

            verify(exactly = 1) {
                deleteFromCartUseCase(any())
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Success(shoppingCart))

        }
    }

    @Nested
    @DisplayName("Given finish order fails ")
    inner class FinishOrderFails {
        @Test
        fun `Then it should pass error response `() {

            every {
                processOrderUseCase()
            } returns Single.error(error)

            val paymentLiveData = underTest.payment.testObserver()

            underTest.finishOrder()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                processOrderUseCase()
            }


            Truth.assert_()
                .that(paymentLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(paymentLiveData.observedValues[1])
                .isEqualTo(Response.Error(error))

        }
    }

    @Nested
    @DisplayName("Given finish order succeeds ")
    inner class FinishOrderSucceeds {
        @Test
        fun `Then it should return items in the cart `() {

            every {
                processOrderUseCase()
            } returns Single.just(shoppingCart)

            val cartLiveData = underTest.cart.testObserver()
            val paymentLiveData = underTest.payment.testObserver()

            underTest.finishOrder()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                processOrderUseCase()
            }


            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Success(shoppingCart))

            Truth.assert_()
                .that(paymentLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(paymentLiveData.observedValues[1])
                .isEqualTo(Response.Success(shoppingCart))

        }
    }

    @Nested
    @DisplayName("Given get orders fails ")
    inner class GetOrdersFails {
        @Test
        fun `Then it should pass error response `() {

            every {
                getOrderUseCase()
            } returns Flowable.error(error)

            val ordersLiveData = underTest.orders.testObserver()

            underTest.getOrders()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                getOrderUseCase()
            }


            Truth.assert_()
                .that(ordersLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(ordersLiveData.observedValues[1])
                .isEqualTo(Response.Error(error))

        }
    }

    @Nested
    @DisplayName("Given get orders succeeds ")
    inner class GetOrdersSucceeds {
        @Test
        fun `Then it should return orders `() {

            every {
                getOrderUseCase()
            } returns Flowable.just(orders)

            val ordersLiveData = underTest.orders.testObserver()

            underTest.getOrders()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                getOrderUseCase()
            }


            Truth.assert_()
                .that(ordersLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(ordersLiveData.observedValues[1])
                .isEqualTo(Response.Success(orders))

        }
    }

    @Nested
    @DisplayName("Given getCartFromDb fails ")
    inner class GetCartFromDbFails {
        @Test
        fun `Then it should pass error response `() {
            every {
                getCartFromDbUseCase()
            } returns Single.error(error)

            val cartLiveData = underTest.cart.testObserver()

            underTest.getCartFromDb()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                getCartFromDbUseCase()
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Error(error))

        }
    }

    @Nested
    @DisplayName("Given getCartFromDb succeeds ")
    inner class GetCartFromDbSucceeds {
        @Test
        fun `Then it should return items in the cart `() {

            every {
                getCartFromDbUseCase()
            } returns Single.just(shoppingCart)

            val cartLiveData = underTest.cart.testObserver()

            underTest.getCartFromDb()

            testScheduler.triggerActions()

            verify(exactly = 1) {
                getCartFromDbUseCase()
            }

            Truth.assert_()
                .that(cartLiveData.observedValues[0])
                .isEqualTo(Response.Loading)

            Truth.assert_()
                .that(cartLiveData.observedValues[1])
                .isEqualTo(Response.Success(shoppingCart))

        }
    }


}