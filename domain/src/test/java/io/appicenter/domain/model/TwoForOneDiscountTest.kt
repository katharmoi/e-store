package io.appicenter.domain.model

import com.google.common.truth.Truth
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigDecimal

internal class TwoForOneDiscountTest {
    lateinit var underTest: TwoForOneDiscount

    @BeforeEach
    fun setUp() {
        underTest = TwoForOneDiscount(
            code = "VOUCHER"
        )
    }

    @Nested
    @DisplayName("Given cart is empty")
    inner class EmptyCart {

        val items: List<OrderItem> = emptyList()

        @Test
        fun `Then it should return zero discount`() {
            Truth.assert_()
                .that(underTest.apply(items))
                .isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    @DisplayName("Given cart is not empty")
    inner class NonEmptyCart {

        @Nested
        @DisplayName("When quantity is less than two")
        inner class UnderMinQty {

            val items = listOf(
                OrderItem(
                    Item("VOUCHER", "Cabify Voucher", BigDecimal(5)),
                    1
                )
            )


            @Test
            fun `Then it should return zero discount`() {
                Truth.assert_()
                    .that(underTest.apply(items))
                    .isEqualTo(BigDecimal.ZERO)
            }
        }

        @Nested
        @DisplayName("When quantity is more or equal to two")
        inner class OverMinQty {

            @ParameterizedTest(name = "{index} ==> given {0} vouchers bought it should return discount of {1}")
            @CsvSource(
                "2,5",
                "3,5",
                "5,10",
                "8,20"
            )
            fun `Then it should return correct discount`(count: Int, expected: BigDecimal) {

                val items = listOf(
                    OrderItem(
                        Item("VOUCHER", "Cabify Voucher", BigDecimal(5)),
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