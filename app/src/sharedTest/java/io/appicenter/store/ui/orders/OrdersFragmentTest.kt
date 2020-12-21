package io.appicenter.store.ui.orders

import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.appicenter.store.R
import io.appicenter.store.SharedTestData.orders
import io.appicenter.store.data.FakeOrdersRepository
import io.appicenter.store.di.TestApp
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


/**
 * Integration test for the Orders screen
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApp::class)
class OrdersFragmentTest {


    @Test
    fun displayEmptyView_whenOrdersHasNoData() {

        //GIVEN - Orders is empty
        FakeOrdersRepository.orders = emptyList()

        //WHEN - on launch
        launchFragmentInContainer<OrdersFragment>(null, R.style.AppTheme)

        //THEN - verify empty orders view is displayed
        onView(withId(R.id.orders_empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayOrders_whenOrdersHasData() {

        //GIVEN - Orders Repository has data
        FakeOrdersRepository.orders = orders

        //WHEN - on launch
        launchFragmentInContainer<OrdersFragment>(null, R.style.AppTheme)


        //THEN - verify orders recycler is displayed
        onView(withId(R.id.orders_recycler_view)).check(matches(isDisplayed()))

    }


}