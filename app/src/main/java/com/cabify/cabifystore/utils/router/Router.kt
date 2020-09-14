package com.cabify.cabifystore.utils.router

interface Router {

    fun closeScreen()

    fun goBack()

    fun showHomeScreen(sourceFragmentTag: String)

    fun showCartScreen(sourceFragmentTag: String)

    fun showOrdersScreen(sourceFragmentTag: String)

}