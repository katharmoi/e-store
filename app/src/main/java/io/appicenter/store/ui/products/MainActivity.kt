package io.appicenter.store.ui.products

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import io.appicenter.store.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import io.appicenter.store.ui.cart.CartFragment
import io.appicenter.store.ui.orders.OrdersFragment
import io.appicenter.store.ui.products.MainActivityViewModel.ScreenStates.*
import io.appicenter.store.utils.activity.ActivityUtils
import io.appicenter.store.utils.custom_views.CustomSnackbar
import io.appicenter.store.utils.router.Router
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

        //observe network status
        viewModel.networkStatus.observe(this, { parseNetworkStatus(it) })
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveCartToDb()
    }

    private fun parseNetworkStatus(networkStatus: Boolean) {
        if (networkStatus) {
            CustomSnackbar.make(layout_main_activity, Snackbar.LENGTH_SHORT).also {
                it.setText(getString(R.string.notification_connected))
                it.view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green_500))
            }.show()
        } else {
            CustomSnackbar.make(layout_main_activity, Snackbar.LENGTH_SHORT).also {
                it.setText(getString(R.string.notification_no_network))
                it.view.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_90))
            }.show()
        }

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