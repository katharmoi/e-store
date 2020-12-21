package io.appicenter.store.ui.products

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.appicenter.store.R
import io.appicenter.store.SharedTestData.mug
import io.appicenter.store.SharedTestData.tshirt
import io.appicenter.store.SharedTestData.voucher
import io.appicenter.store.SharedTestUtils.clickItemWithId
import io.appicenter.store.SharedTestUtils.launchActivity
import io.appicenter.store.data.FakeCartRepository
import io.appicenter.store.data.FakeNetworkUtils
import io.appicenter.store.data.FakeProductsRepository
import io.appicenter.store.di.TestApp
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.domain.model.TwoForOneDiscount
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


/**
 * Integration test for the Products screen
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApp::class)
class ProductsFragmentTest {
    
    @Test
    fun displayEmptyView_whenRepositoryHasNoData() {

        //GIVEN - Products Repository is empty
        FakeProductsRepository.items = Flowable.just(emptyList())
        FakeNetworkUtils.connected = false

        //WHEN - on launch
        launchActivity()

        //THEN - verify empty products view is displayed
        onView(withId(R.id.products_empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayProducts_whenRepositoryHasData() {

        //GIVEN - Products Repository has data
        FakeProductsRepository.items = Flowable.just(listOf(tshirt, voucher, mug))

        //WHEN - on launch
        launchActivity()


        //THEN - verify products recycler is displayed
        onView(withId(R.id.products_recycler_view)).check(matches(isDisplayed()))

    }

    @Test
    fun displayDiscountTextView_whenThereAreDiscountableItems() {

        //GIVEN - Products Repository has data
        FakeProductsRepository.items = Flowable.just(listOf(tshirt, voucher, mug))

        //GIVEN - Shopping cart has 2 for 1 discount on tshirt
        FakeCartRepository.shoppingCart =
            ShoppingCart(TwoForOneDiscount("TSHIRT"))

        launchActivity()

        //WHEN - added two tshirt
        onView(withId(R.id.products_recycler_view))
            .perform(
                actionOnItemAtPosition<ProductsViewHolder>(0, clickItemWithId(R.id.btn_item_add))
            )
        onView(withId(R.id.products_recycler_view))
            .perform(
                actionOnItemAtPosition<ProductsViewHolder>(0, clickItemWithId(R.id.btn_item_add))
            )


        //THEN - verify discount text is displayed
        onView(withText(TwoForOneDiscount::class.java.simpleName + " is applied")).check(
            matches(
                isDisplayed()
            )
        )

    }

}