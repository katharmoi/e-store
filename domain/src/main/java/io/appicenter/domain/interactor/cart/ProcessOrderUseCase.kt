package io.appicenter.domain.interactor.cart

import io.appicenter.domain.interactor.orders.AddOrderUseCase
import io.appicenter.domain.model.*
import io.reactivex.Single
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
                )
            }
            .andThen(clearCartUseCase())

    }
}