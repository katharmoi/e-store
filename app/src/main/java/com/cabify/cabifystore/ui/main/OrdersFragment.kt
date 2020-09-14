package com.cabify.cabifystore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cabify.cabifystore.R
import dagger.android.support.DaggerFragment

class OrdersFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    companion object {
        val TAG: String = OrdersFragment::class.java.simpleName
        fun newInstance() = OrdersFragment()
    }
}