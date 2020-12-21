package io.appicenter.domain.model

import com.google.common.truth.Truth
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ShoppingCartTest {

    lateinit var underTest: ShoppingCart

    private val tshirt = Item(
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = 20.toBigDecimal()
    )

    private val voucher = Item(
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = 5.toBigDecimal()
    )

    private val mug = Item(
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = BigDecimal(7.5)
    )

    private val bulkPurchaseDiscount = BulkPurchaseDiscount(
        code = "TSHIRT",
        discount = BigDecimal.ONE,
        minQty = 2
    )

    private val twoForOneDiscount = TwoForOneDiscount(
        code = "VOUCHER"
    )


    @BeforeEach
    fun setUp() {
        underTest = ShoppingCart(bulkPurchaseDiscount, twoForOneDiscount)
    }

    @Test
    fun `should add to cart correctly`() {
        underTest.add(tshirt)

        Truth.assert_()
            .that(underTest.cart[0].count)
            .isEqualTo(1)
    }

    @Test
    fun `should only increment the count if item exists`() {
        for (i in 1..3) {
            underTest.add(tshirt)
        }

        Truth.assert_()
            .that(underTest.get(tshirt)?.count)
            .isEqualTo(3)

        Truth.assert_()
            .that(underTest.size)
            .isEqualTo(1)
    }

    @Test
    fun `should update correctly`() {
        underTest.add(tshirt)

        underTest.update(tshirt, 3)

        Truth.assert_()
            .that(underTest.get(tshirt)?.count)
            .isEqualTo(3)
    }

    @Test
    fun `should remove item if update qty is zero`() {
        underTest.add(tshirt)

        underTest.update(tshirt, 0)

        Truth.assert_()
            .that(underTest.size)
            .isEqualTo(0)
    }

    @Test
    fun `should delete the item correctly`() {
        underTest.add(tshirt)

        underTest.delete(tshirt)

        Truth.assert_()
            .that(underTest.size)
            .isEqualTo(0)
    }

    @Test
    fun `should clear the cart correctly`() {

        underTest.add(tshirt)
        underTest.add(voucher)
        underTest.add(mug)

        underTest.clear()

        Truth.assert_()
            .that(underTest.size)
            .isEqualTo(0)

        Truth.assert_()
            .that(underTest.appliedDiscounts)
            .isEmpty()

        Truth.assert_()
            .that(underTest.total)
            .isEqualTo(0.toBigDecimal())

        Truth.assert_()
            .that(underTest.totalAfterDiscounts)
            .isEqualTo(0.toBigDecimal())

        Truth.assert_()
            .that(underTest.totalDiscounts)
            .isEqualTo(0.toBigDecimal())
    }

    @Test
    fun `should calculate total correctly for Items VOUCHER, TSHIRT, MUG`() {
        underTest.add(voucher)
        underTest.add(tshirt)
        underTest.add(mug)

        Truth.assert_()
            .that(underTest.totalAfterDiscounts)
            .isEqualTo(BigDecimal(32.5))
    }

    @Test
    fun `should calculate total correctly for Items VOUCHER, TSHIRT, VOUCHER`() {
        underTest.add(voucher)
        underTest.add(tshirt)
        underTest.add(voucher)

        Truth.assert_()
            .that(underTest.totalAfterDiscounts)
            .isEqualTo(BigDecimal(25))
    }

    @Test
    fun `should calculate total correctly for Items TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT`() {
        underTest.add(tshirt)
        underTest.add(tshirt)
        underTest.add(tshirt)
        underTest.add(voucher)
        underTest.add(tshirt)

        Truth.assert_()
            .that(underTest.totalAfterDiscounts)
            .isEqualTo(BigDecimal(81))
    }

    @Test
    fun `should calculate total correctly for Items VOUCHER, TSHIRT, VOUCHER, VOUCHER, MUG, TSHIRT, TSHIRT`() {
        underTest.add(voucher)
        underTest.add(tshirt)
        underTest.add(voucher)
        underTest.add(voucher)
        underTest.add(mug)
        underTest.add(tshirt)
        underTest.add(tshirt)

        Truth.assert_()
            .that(underTest.totalAfterDiscounts)
            .isEqualTo(BigDecimal(74.50))
    }

}