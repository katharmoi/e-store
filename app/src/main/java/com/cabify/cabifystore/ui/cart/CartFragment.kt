package com.cabify.cabifystore.ui.cart

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.products.MainActivityViewModel
import com.cabify.cabifystore.ui.products.MainActivityViewModelFactory
import com.cabify.cabifystore.utils.Response
import com.cabify.domain.model.ShoppingCart
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import javax.inject.Inject


class CartFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var cartAdapter: CartAdapter

    private lateinit var cartRecycler: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        //Initialize cart recycler
        cartAdapter = CartAdapter(
            itemView = R.layout.list_item_cart,
            emptyView = view.cart_empty_view,
            factory = { v: View -> CartViewHolder(v) },
            itemListener = null
        )

        cartRecycler = view.cart_recycler_view
        cartRecycler.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = cartAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cart_pay.setOnClickListener {
            activity?.main_progress?.visibility = View.VISIBLE
            Handler().postDelayed({ viewModel.finishOrder() }, 1000)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel =
                ViewModelProvider(it, viewModelFactory).get(MainActivityViewModel::class.java)
        }

        viewModel.cart.observe(
            viewLifecycleOwner,
            Observer<Response<ShoppingCart>> { parseCart(it) }
        )

        viewModel.payment.observe(
            viewLifecycleOwner,
            Observer<Response<ShoppingCart>> { parsePayment(it) }
        )
    }

    private fun parseCart(cartResponse: Response<ShoppingCart>) {
        when (cartResponse) {
            is Response.Success -> {
                Log.e("CART FR PARSE CALLED:","CALLED")
                cart_pay.isEnabled = cartResponse.data.cart.isNotEmpty()
                fillCartData(cartResponse.data)
            }
            is Response.Error -> {

            }
            Response.Loading -> {
            }
        }
    }

    private fun parsePayment(paymentResponse: Response<ShoppingCart>) {
        when (paymentResponse) {
            is Response.Success -> {
                Log.e("PAYMENT CALLED:","CALLED")
                activity?.main_progress?.visibility = View.GONE
                Toast.makeText(
                    context,
                    getString(R.string.successful_payment),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Response.Error -> {
                activity?.main_progress?.visibility = View.GONE
                Toast.makeText(
                    context,
                    getString(R.string.unsuccessful_payment),
                    Toast.LENGTH_SHORT
                ).show()

            }
            Response.Loading -> {
                activity?.main_progress?.visibility = View.VISIBLE
            }
        }
    }

    private fun fillCartData(cart: ShoppingCart) {
        cartAdapter.items = cart.cart.map { it.copy() }
        val isVisible = if (cart.cart.isEmpty()) View.GONE else View.VISIBLE
        cart_total.visibility = isVisible
        cart_total.text = getString(R.string.total_template, "€", cart.total)
        cart_discounts.visibility = isVisible
        var discountsText = "\n"
        for (discount in cart.appliedDiscounts) {
            discountsText += discount.key + " amount: " + "€" + discount.value + "\n"
        }
        cart_discounts.text =
            getString(R.string.discounts_template, discountsText)
        cart_total_after_discounts.visibility = isVisible
        cart_total_after_discounts.text = getString(
            R.string.total_after_discounts_template, "€",
            cart.totalAfterDiscounts
        )
    }

    companion object {
        val TAG: String = CartFragment::class.java.simpleName
        fun newInstance() = CartFragment()
    }

}