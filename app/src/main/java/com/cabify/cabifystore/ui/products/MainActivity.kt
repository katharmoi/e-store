package com.cabify.cabifystore.ui.products

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.cart.CartFragment
import com.cabify.cabifystore.ui.products.MainActivityViewModel.ScreenStates.*
import com.cabify.cabifystore.ui.orders.OrdersFragment
import com.cabify.cabifystore.utils.activity.ActivityUtils
import com.cabify.cabifystore.utils.router.Router
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    @Inject
    lateinit var activityUtils: ActivityUtils

    @Inject
    lateinit var router: Router

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        //Initialize ViewModel
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainActivityViewModel::class.java)

        //Load HomeFragment At App Start
        if (savedInstanceState == null) {
            activityUtils.addFragmentWithTagToActivity(
                supportFragmentManager,
                ProductsFragment.newInstance(),
                R.id.main_container,
                ProductsFragment.TAG
            )
            viewModel.currentState = HOME
        }

        //Bind navigation
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (viewModel.currentState == HOME) {
            router.goBack()
        } else {
            router.showHomeScreen(sourceFragmentTag(R.id.navigation_item_home))
            navigationBar.selectedItemId = R.id.navigation_item_home
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //On selecting existing item do nothing
        val currentItemId = navigationBar.selectedItemId
        if (item.itemId == currentItemId) return false

        when (item.itemId) {
            R.id.navigation_item_home -> {
                viewModel.currentState = HOME
                router.showHomeScreen(sourceFragmentTag(currentItemId))
                return true
            }
            R.id.navigation_item_orders -> {
                viewModel.currentState = ORDERS
                router.showOrdersScreen(sourceFragmentTag(currentItemId))
                return true
            }
            R.id.navigation_item_cart -> {
                viewModel.currentState = CART
                router.showCartScreen(sourceFragmentTag(currentItemId))
                return true
            }
        }
        return false
    }

    private fun sourceFragmentTag(id: Int): String {
        when (id) {
            R.id.navigation_item_home -> return ProductsFragment.TAG
            R.id.navigation_item_orders -> return OrdersFragment.TAG
            R.id.navigation_item_cart -> return CartFragment.TAG
        }
        return ""
    }
}