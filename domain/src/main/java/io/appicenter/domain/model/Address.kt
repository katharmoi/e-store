package io.appicenter.domain.model

data class Address(
    val addressOne: String,
    val addressTwo: String?,
    val city: String?,
    val postcode: String?,
    val country: String,
    val phoneNumber: String
) {
    override fun toString(): String {
        return this.addressOne + " " + this.addressTwo + "\n" + "City: " + city + "\n" + "Post Code: " + postcode + "\n" + "Country: " + country + "\n" + "Phone Number: " + phoneNumber
    }
}