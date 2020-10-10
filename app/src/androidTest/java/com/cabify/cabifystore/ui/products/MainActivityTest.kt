package com.cabify.cabifystore.ui.products

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.cabify.cabifystore.IdlingResourceRule
import com.cabify.cabifystore.R
import com.cabify.cabifystore.SharedTestUtils.atPosition
import com.cabify.cabifystore.SharedTestUtils.clickItemWithId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * End-to-End tests
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    //Idling resource for okHttp
    @get:Rule
    var rule = IdlingResourceRule()

    @Test
    fun addProductsToCartAndRecreateActivity_cartIsRepopulatedOnResumed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        //Add products to cart
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    0,
                    clickItemWithId(R.id.btn_item_add)
                )
            )
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    1,
                    clickItemWithId(R.id.btn_item_add)
                )
            )

        //Recreate activity
        activityScenario.recreate()

        //Check first item has the correct nof items in the cart
        onView(withId(R.id.products_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText("1")))))

        //Check second item has the correct nof items in the cart
        onView(withId(R.id.products_recycler_view))
            .check(matches(atPosition(1, hasDescendant(withText("1")))))
    }

    @Test
    fun addProductsToCartAndGoToCartScreen_itemsAreListedOnTheCartScreen() {

        ActivityScenario.launch(MainActivity::class.java)

        //Add products to cart
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    0,
                    clickItemWithId(R.id.btn_item_add)
                )
            )
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    1,
                    clickItemWithId(R.id.btn_item_add)
                )
            )

        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    2,
                    clickItemWithId(R.id.btn_item_add)
                )
            )

        //Click cart navigation item
        onView(withId(R.id.navigation_item_cart)).perform(click())

        //Verify items are added
        onView(withId(R.id.cart_recycler_view)).check(matches(isDisplayed()))

        //Check first item has the correct nof items in the cart
        onView(withId(R.id.cart_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText("1")))))

        //Check second item has the correct nof items in the cart
        onView(withId(R.id.cart_recycler_view))
            .check(matches(atPosition(1, hasDescendant(withText("1")))))

    }

    @Test
    fun addProductsToCartAndCheckOut_orderIsListedOnTheOrdersScreen() {

        ActivityScenario.launch(MainActivity::class.java)

        //Add products to cart
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    0,
                    clickItemWithId(R.id.btn_item_add)
                )
            )
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    1,
                    clickItemWithId(R.id.btn_item_add)
                )
            )

        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsViewHolder>(
                    2,
                    clickItemWithId(R.id.btn_item_add)
                )
            )

        //Click cart navigation item
        onView(withId(R.id.navigation_item_cart)).perform(click())

        //Press pay btn
        onView(withId(R.id.cart_pay)).perform(customScrollTo, click())

        //Click orders navigation item
        onView(withId(R.id.navigation_item_orders)).perform(click())

        //Verify order is added
        onView(withId(R.id.orders_recycler_view)).check(matches(isDisplayed()))


    }

    private var customScrollTo: ViewAction = object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return allOf(
                withEffectiveVisibility(Visibility.VISIBLE), isDescendantOfA(
                    anyOf(
                        isAssignableFrom(ScrollView::class.java),
                        isAssignableFrom(HorizontalScrollView::class.java),
                        isAssignableFrom(NestedScrollView::class.java)
                    )
                )
            )
        }

        override fun getDescription(): String {
            return "Perform scroll"
        }

        override fun perform(uiController: UiController?, view: View?) {
            ScrollToAction().perform(uiController, view)
        }
    }
}