package com.cabify.cabifystore.utils.router

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.cart.CartFragment
import com.cabify.cabifystore.ui.products.ProductsFragment
import com.cabify.cabifystore.ui.orders.OrdersFragment
import com.cabify.domain.model.PerActivity
import javax.inject.Inject

@PerActivity
class RouterImpl @Inject constructor(
    private val activity: Activity,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Router {

    override fun showOrdersScreen(sourceFragmentTag: String) {
        advanceToFragmentWithAction(
            OrdersFragment.TAG,
            sourceFragmentTag,
            { OrdersFragment.newInstance() },
            null
        )
    }


    override fun showCartScreen(sourceFragmentTag: String) {
        advanceToFragmentWithAction(
            CartFragment.TAG,
            sourceFragmentTag,
            { CartFragment.newInstance() },
            null
        )
    }


    override fun showHomeScreen(sourceFragmentTag: String) {
        advanceToFragmentWithAction(
            ProductsFragment.TAG,
            sourceFragmentTag,
            { ProductsFragment.newInstance() },
            null
        )

    }

    override fun closeScreen() {
        activity.finish()
    }

    override fun goBack() {
//        if (fragmentManager.backStackEntryCount == 0) {
//            activity.finish()
//        } else {
//            fragmentManager.popBackStack()
//        }
        activity.finish()
    }


    /**
     * Transitions to destination fragment.
     * If fragment exists in back-stack hides source fragment and shows destination fragment
     * else creates new instance of destination fragment and hides source fragment
     */
    private fun advanceToFragmentWithAction(
        destinationFragmentTag: String,
        sourceFragmentTag: String,
        destinationFragmentFactory: () -> Fragment,
        destinationFragmentExistsAction: ((Fragment) -> Unit)?
    ) {

        var destinationFragment = fragmentManager.findFragmentByTag(destinationFragmentTag)
        val sourceFragment = fragmentManager.findFragmentByTag(sourceFragmentTag)

        if (destinationFragment == null) {
            destinationFragment = destinationFragmentFactory.invoke()
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_enter,
                    R.anim.fragment_left_exit,
                    R.anim.fragment_left_enter,
                    R.anim.fragment_right_exit
                )
                .addToBackStack(destinationFragmentTag)
                .hide(sourceFragment!!)
                .add(containerId, destinationFragment, destinationFragmentTag)
                .commit()

        } else {
            destinationFragmentExistsAction?.invoke(destinationFragment)
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_enter,
                    R.anim.fragment_left_exit,
                    R.anim.fragment_left_enter,
                    R.anim.fragment_right_exit
                )
                .addToBackStack(destinationFragmentTag)
                .hide(sourceFragment!!)
                .show(destinationFragment)
                .commit()


        }

    }
}