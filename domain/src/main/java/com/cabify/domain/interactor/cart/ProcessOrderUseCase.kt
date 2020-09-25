package com.cabify.domain.interactor.cart

import com.cabify.domain.interactor.orders.AddOrderUseCase
import com.cabify.domain.model.*
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@PerActivity
class ProcessOrderUseCase @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val addOrderUseCase: AddOrderUseCase
) {

    operator fun invoke(): Single<ShoppingCart> {
        return getCartUseCase()
            .flatMapCompletable { cart ->
                addOrderUseCase(
                    Order(
                        items = cart.cart,
                        customer = Customer(
                            name = "Kadir",
                            surname = "Kertis",
                            email = "test@test.com",
                            address = Address(
                                addressOne = "addr 1",
                                addressTwo = "addr 2",
                                city = "Izmir",
                                postcode = "3444",
                                country = "Turkey",
                                phoneNumber = "testphone"
                            ),
                            language = "tr"

                        ),
                        total = cart.totalAfterDiscounts,
                        date = Date()
                )
                ) }
            .andThen(clearCartUseCase())

    }
}