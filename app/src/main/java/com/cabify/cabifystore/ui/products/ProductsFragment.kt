package com.cabify.cabifystore.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabifystore.R
import com.cabify.cabifystore.utils.Response
import com.cabify.data.utils.transform
import com.cabify.domain.model.Item
import com.cabify.domain.model.OrderItem
import com.cabify.domain.model.ShoppingCart
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_products.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.math.BigDecimal
import javax.inject.Inject

class ProductsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

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

        viewModel.items.observe(
            viewLifecycleOwner,
            Observer<Response<List<Item>>> { parseItems(it) }
        )

        viewModel.cart.observe(
            viewLifecycleOwner,
            Observer<Response<ShoppingCart>> { parseCart(it) }
        )
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
                activity?.main_progress?.visibility = View.GONE

                val cart = response.data.cart
                productsAdapter.items.map {
                    val cartItem = cart.find { cartItem -> cartItem.item.code == it.item.code }
                    it.count = cartItem?.count ?: 0
                    it.applicableDiscount = cartItem?.applicableDiscount
                    it.discount = cartItem?.discount
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

                if (productsAdapter.items.isEmpty())
                    productsAdapter.items = response.data.transform { OrderItem(it, 0) }

                //Display empty view if can't get any item
                products_empty_view.visibility =
                    if (response.data.isEmpty()) View.VISIBLE else View.GONE
            }
            is Response.Error -> {
                activity?.main_progress?.visibility = View.GONE
                products_empty_view.visibility =
                    if (productsAdapter.items.isEmpty()) View.VISIBLE else View.GONE

            }
            Response.Loading -> {
                activity?.main_progress?.visibility = View.VISIBLE
            }
        }
    }

    private fun updateCartNavigationItem(total: BigDecimal, count: Int) {
        val navBar = activity?.navigationBar

        navBar?.apply {
            getOrCreateBadge(R.id.navigation_item_cart)?.apply {
                this.isVisible = (count != 0)
                this.number = count
            }

            menu.findItem(R.id.navigation_item_cart)?.title =
                if (total != 0.toBigDecimal()) getString(R.string.price_template, "€", total)
                else getString(R.string.navigation_item_cart)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveCartToDb()
    }

    companion object {
        val TAG: String = ProductsFragment::class.java.simpleName
        fun newInstance() = ProductsFragment()
    }


}