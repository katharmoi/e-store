package io.appicenter.store.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.appicenter.store.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.appicenter.data.utils.transform
import io.appicenter.domain.model.Item
import io.appicenter.domain.model.OrderItem
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.domain.utils.NetworkUtils
import io.appicenter.store.utils.Response
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_products.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.math.BigDecimal
import javax.inject.Inject

class ProductsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    @Inject
    lateinit var networkUtils: NetworkUtils

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var productsAdapter: ProductsAdapter

    private lateinit var productsRecycler: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //Initialize products recycler
        productsAdapter = ProductsAdapter(
            itemView = R.layout.list_item_product,
            emptyView = null,
            factory = { v: View -> ProductsViewHolder(v, viewModel) },
            itemListener = null
        )

        productsRecycler = view.products_recycler_view
        productsRecycler.adapter = productsAdapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel =
                ViewModelProvider(it, viewModelFactory).get(MainActivityViewModel::class.java)
        }

        viewModel.items.observe(viewLifecycleOwner, { parseItems(it) })

        viewModel.cart.observe(viewLifecycleOwner, { parseCart(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        empty_retry.setOnClickListener {
            viewModel.getItems()
        }
    }

    private fun parseCart(response: Response<ShoppingCart>) {
        when (response) {
            is Response.Success -> {
                val cart = response.data.cart
                productsAdapter.items.map { orderItem ->
                    val cartItem =
                        cart.find { cartItem -> cartItem.item.code == orderItem.item.code }
                    orderItem.count = cartItem?.count ?: 0
                    orderItem.applicableDiscount = cartItem?.applicableDiscount
                    orderItem.discount = cartItem?.discount
                }

                productsAdapter.notifyDataSetChanged()

                //Display cart unique items as a badge on cart and total as title
                updateCartNavigationItem(response.data.totalAfterDiscounts, response.data.cart.size)
            }
            is Response.Error -> {
            }
            Response.Loading -> {
            }
        }
    }

    private fun parseItems(response: Response<List<Item>>) {
        when (response) {
            is Response.Success -> {
                activity?.main_progress?.visibility = View.GONE
                products_empty_view.visibility = View.GONE

                if (productsAdapter.items.isEmpty())
                    productsAdapter.items = response.data.transform { OrderItem(it, 0) }

                if (!networkUtils.isConnected()) displayNoNetworkState()

            }
            is Response.Error -> {
                activity?.main_progress?.visibility = View.GONE
            }
            Response.Loading -> {
                activity?.main_progress?.visibility = View.VISIBLE
            }
        }
    }

    private fun updateCartNavigationItem(total: BigDecimal, count: Int) {
        val navBar = activity?.navigationBar

        navBar?.apply {
            if (count > 0) {
                getOrCreateBadge(R.id.navigation_item_cart)?.number = count
            } else {
                removeBadge(R.id.navigation_item_cart)
            }

            menu.findItem(R.id.navigation_item_cart)?.title =
                if (total != 0.toBigDecimal()) getString(R.string.price_template, "€", total)
                else getString(R.string.navigation_item_cart)
        }
    }

    private fun displayNoNetworkState() {
        if (productsAdapter.items.isNotEmpty()) {
            //Display a snackbar to notify user about cached data
            Snackbar.make(
                layout_products,
                getString(R.string.notification_cached_data),
                Snackbar.LENGTH_SHORT
            ).show()

        } else {
            products_empty_view.visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG: String = ProductsFragment::class.java.simpleName
        fun newInstance() = ProductsFragment()
    }
}