package io.appicenter.store.ui.cart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.appicenter.store.R
import dagger.android.support.DaggerFragment
import io.appicenter.domain.model.ShoppingCart
import io.appicenter.store.ui.products.MainActivityViewModel
import io.appicenter.store.ui.products.MainActivityViewModelFactory
import io.appicenter.store.utils.Response
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
            emptyView = null,
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
            Handler(Looper.getMainLooper()).postDelayed({ viewModel.finishOrder() }, 1000)
        }
        cart_empty_view.visibility = if (cartAdapter.items.isEmpty()) View.VISIBLE
        else View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel =
                ViewModelProvider(it, viewModelFactory).get(MainActivityViewModel::class.java)
        }

        viewModel.cart.observe(
            viewLifecycleOwner,
            { parseCart(it) }
        )

        viewModel.payment.observe(
            viewLifecycleOwner,
            { parsePayment(it) }
        )
    }

    private fun parseCart(cartResponse: Response<ShoppingCart>) {
        when (cartResponse) {
            is Response.Success -> {
                cart_pay.isEnabled = cartResponse.data.cart.isNotEmpty()
                fillCartData(cartResponse.data)
                cart_empty_view.visibility = if (cartResponse.data.cart.isEmpty()) View.VISIBLE
                else View.GONE
            }
            is Response.Error -> {
                Toast.makeText(
                    context,
                    getString(R.string.error_cart),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Response.Loading -> {
            }
        }
    }

    private fun parsePayment(paymentResponse: Response<ShoppingCart>) {
        when (paymentResponse) {
            is Response.Success -> {
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