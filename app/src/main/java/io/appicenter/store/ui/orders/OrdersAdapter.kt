package io.appicenter.store.ui.orders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.appicenter.domain.model.Order
import io.appicenter.store.ui.base.BaseAdapter
import io.appicenter.store.ui.base.OnItemSelectListener
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