package com.cabify.cabifystore

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import com.cabify.cabifystore.ui.products.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher

object SharedTestUtils {

    fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            val recyclerView = (activity.findViewById(R.id.products_recycler_view) as RecyclerView)
            recyclerView.itemAnimator = null
        }
        return activityScenario
    }


    fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Perform click on a child view with specified id"
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById(id) as View
                v.performClick()
            }
        }
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {

        return object : BoundedMatcher<View?, RecyclerView?>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

           override fun matchesSafely(view: RecyclerView?): Boolean {
                val viewHolder = view?.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }


        }
    }


}