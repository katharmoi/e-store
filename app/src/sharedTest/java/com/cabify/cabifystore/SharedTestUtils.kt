package com.cabify.cabifystore

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import com.cabify.cabifystore.ui.products.MainActivity
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
}