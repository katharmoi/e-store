package com.cabify.cabifystore.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.products.MainActivityViewModel
import com.cabify.cabifystore.ui.products.MainActivityViewModelFactory
import com.cabify.cabifystore.utils.Response
import com.cabify.domain.model.Order
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_orders.view.*
import javax.inject.Inject

class OrdersFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var ordersAdapter: OrdersAdapter

    private lateinit var ordersRecycler: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        //Initialize orders recycler
        ordersAdapter = OrdersAdapter(
            itemView = R.layout.list_item_order,
            emptyView = view.orders_empty_view,
            factory = { v: View -> OrdersViewHolder(v) },
            itemListener = null
        )

        ordersRecycler = view.orders_recycler_view
        ordersRecycler.adapter = ordersAdapter


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel =
                ViewModelProvider(it, viewModelFactory).get(MainActivityViewModel::class.java)
        }


        viewModel.orders.observe(
            viewLifecycleOwner,
            { parseOrders(it) }
        )

        viewModel.getOrders()
    }

    private fun parseOrders(response: Response<List<Order>>) {
        when (response) {
            is Response.Success -> {
                activity?.main_progress?.visibility = View.GONE
                ordersAdapter.items = response.data.map { it.copy() }
            }
            is Response.Error -> {
                activity?.main_progress?.visibility = View.GONE
            }
            Response.Loading -> {
                activity?.main_progress?.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        val TAG: String = OrdersFragment::class.java.simpleName
        fun newInstance() = OrdersFragment()
    }


}