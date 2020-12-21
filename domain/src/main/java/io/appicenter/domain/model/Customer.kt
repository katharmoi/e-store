package io.appicenter.domain.model

data class Customer(
    val name: String,
    val surname: String,
    val email: String,
    val address: Address,
    val language:String
)