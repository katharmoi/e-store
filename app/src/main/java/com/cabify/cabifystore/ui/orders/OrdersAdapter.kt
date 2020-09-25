package com.cabify.cabifystore.ui.orders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabifystore.ui.base.BaseAdapter
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.Order
import kotlin.properties.Delegates


class OrdersAdapter(
    itemView: Int,
    emptyView: View,
    factory: (view: View) -> OrdersViewHolder,
    val itemListener: OnItemSelectListener?
) : BaseAdapter<OrdersViewHolder, Order>(itemView, emptyView, factory, itemListener) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override var items: List<Order> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

}