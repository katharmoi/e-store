package io.appicenter.domain.model

import com.google.common.truth.Truth
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigDecimal

internal class BulkPurchaseDiscountTest {

    lateinit var underTest: BulkPurchaseDiscount

    @Nested
    @DisplayName("Given cart is empty")
    inner class EmptyCart {
        @BeforeEach
        fun setUp() {
            underTest = BulkPurchaseDiscount(
                code = "TSHIRT",
                discount = BigDecimal.ONE,
                minQty = 2
            )
        }

        @Test
        fun `Then it should return zero discount`() {
            Truth.assert_()
                .that(underTest.apply(emptyList()))
                .isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    @DisplayName("Given cart is not empty")
    inner class NonEmptyCart {

        private val discount: BigDecimal = BigDecimal.ONE
        private val minQty = 3

        @BeforeEach
        fun setUp() {
            underTest = BulkPurchaseDiscount(
                code = "TSHIRT",
                discount = discount,
                minQty = minQty
            )
        }

        @Nested
        @DisplayName("When quantity is less than minimum qty")
        inner class UnderMinQty {
            @Test
            fun `Then it should return zero discount`() {
                val items = listOf(
                    OrderItem(
                        Item("TSHIRT", "Cabify T-Shirt", BigDecimal(20)),
                        2
                    )
                )

                Truth.assert_()
                    .that(underTest.apply(items))
                    .isEqualTo(BigDecimal.ZERO)
            }
        }

        @Nested
        @DisplayName("When quantity is more or equal to minimum qty")
        inner class OverMinQty {

            @ParameterizedTest(name = "{index} ==> given {0} should return {1}")
            @CsvSource(
                "3,3",
                "5,5",
                "14,14",
                "121,121"
            )
            fun `Then it should return correct discount`(count: Int, expected: BigDecimal) {
                val items = listOf(
                    OrderItem(
                        Item("TSHIRT", "Cabify T-Shirt", BigDecimal(20)),
                        count
                    )
                )
                Truth.assert_()
                    .that(underTest.apply(items))
                    .isEqualTo(expected)
            }
        }


    }
}