package io.appicenter.data.products

import io.appicenter.data.Utils.mug
import io.appicenter.data.Utils.tshirt
import io.appicenter.data.Utils.voucher
import io.appicenter.data.products.adapters.toData
import io.appicenter.data.products.db.ProductsDao
import io.appicenter.data.products.service.ProductsService
import io.appicenter.domain.model.Item
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.AfterClass
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.ConnectException

internal class  ProductsRepositoryImplTest {

    lateinit var underTest: ProductsRepositoryImpl

    private val productsService: ProductsService = mockk()
    private val productsStore: ProductsDao = mockk()

    val subscriber: TestSubscriber<List<Item>> = TestSubscriber()

    @BeforeEach
    fun setUp() {
        underTest = ProductsRepositoryImpl(productsService, productsStore)
    }

    @AfterClass
    fun tearDown() {
        if (!subscriber.isDisposed) subscriber.dispose()
    }

    @Nested
    @DisplayName("Given products table is empty")
    inner class EmptyProductsTable {
        @BeforeEach
        fun setUp() {
            every {
                productsStore.getAll()
            } returns Flowable.empty()
        }

        @Nested
        @DisplayName("When products service fails")
        inner class ProductsServiceFail {
            @BeforeEach
            fun setUp() {
                every {
                    productsService.getAll()
                } returns Single.error(ConnectException())
            }

            @Test
            fun `Then it should not emit a value and propagate error`() {
                underTest.get().subscribe(subscriber)

                subscriber.assertNoValues()
                    .assertError(ConnectException::class.java)

            }
        }

        @Nested
        @DisplayName("When products service succeeds")
        inner class ProductsServiceSuccessful {
            @BeforeEach
            fun setUp() {
                every {
                    productsService.getAll()
                } returns Single.just(listOf(tshirt.toData(), voucher.toData()))

                every {
                    productsStore.addAll(any())
                } returns Completable.complete()
            }

            @Test
            fun `Then it should emit rows`() {
                underTest.get().subscribe(subscriber)
                

            }
        }
    }

    @Nested
    @DisplayName("Given products table is not empty")
    inner class ProductsTableHasItems {
        @BeforeEach
        fun setUp() {
            every {
                productsStore.getAll()
            } returns Flowable.just(listOf(tshirt.toData(), mug.toData()))
        }

        @Nested
        @DisplayName("When products service fails")
        inner class ProductsServiceFail {
            @BeforeEach
            fun setUp() {
                every {
                    productsService.getAll()
                } returns Single.error(ConnectException())
            }

            @Test
            fun `Then it should emit rows and propagate error`() {
                underTest.get().subscribe(subscriber)

                subscriber.assertValue(listOf(tshirt, mug))
                    .assertError(ConnectException::class.java)
                    .assertNotComplete()

            }
        }
    }

    @Nested
    @DisplayName("Given products table is not empty")
    inner class ProductsTableNotEmpty {
        @BeforeEach
        fun setUp() {
            every {
                productsStore.getAll()
            } returns Flowable.just(listOf(tshirt.toData(), voucher.toData()))
        }

        @Nested
        @DisplayName("When products service succeeds")
        inner class ProductsServiceSuccessful {
            @BeforeEach
            fun setUp() {
                every {
                    productsService.getAll()
                } returns Single.just(emptyList())

                every {
                    productsStore.addAll(any())
                } returns Completable.complete()
            }

            @Test
            fun `Then it should emit rows`() {
                underTest.get().subscribe(subscriber)

                subscriber.assertValue(listOf(tshirt, voucher))
                    .assertNoErrors()
                    .assertComplete()
            }
        }
    }


}