package io.appicenter.data.order.model

import androidx.room.Embedded
import io.appicenter.domain.model.Address

data class DataCustomer(
    val name: String,
    val surname: String,
    val email: String,
    @Embedded val address: Address,
    val language: String
)