package io.appicenter.store.ui.cart

import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.appicenter.store.R
import io.appicenter.store.SharedTestData.tshirt
import io.appicenter.store.data.FakeCartRepository
import io.appicenter.store.di.TestApp
import io.appicenter.domain.model.ShoppingCart
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


/**
 * Integration test for the Cart screen
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApp::class)
class CartFragmentTest {


    @Test
    fun displayEmptyView_whenCartHasNoData() {

        //GIVEN - Shopping cart is empty
        FakeCartRepository.shoppingCart = ShoppingCart()

        //WHEN - on launch
        launchFragmentInContainer<CartFragment>(null, R.style.AppTheme)

        //THEN - verify empty cart view is displayed
        onView(withId(R.id.cart_empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayCart_whenCartHasData() {

        //GIVEN - Cart Repository has data
        FakeCartRepository.shoppingCart.add(tshirt)

        //WHEN - on launch
        launchFragmentInContainer<CartFragment>(null, R.style.AppTheme)


        //THEN - verify cart recycler is displayed
        onView(withId(R.id.cart_recycler_view)).check(matches(isEnabled()))

    }

    @Test
    fun payBtnDisabled_whenCartIsEmpty() {

        //GIVEN - Shopping cart is empty
        FakeCartRepository.shoppingCart = ShoppingCart()

        //WHEN - on launch
        launchFragmentInContainer<CartFragment>(null, R.style.AppTheme)

        //THEN - verify pay btn is not enabled
        onView(withId(R.id.cart_pay)).check(matches(not(isEnabled())))

    }

}