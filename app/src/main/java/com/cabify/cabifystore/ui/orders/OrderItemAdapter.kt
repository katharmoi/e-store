package com.cabify.cabifystore.ui.orders

import android.view.View
import com.cabify.cabifystore.ui.base.BaseAdapter
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.OrderItem
import kotlin.properties.Delegates


class OrderItemAdapter(
    itemView: Int,
    factory: (view: View) -> OrderItemViewHolder,
    itemListener: OnItemSelectListener?
) : BaseAdapter<OrderItemViewHolder, OrderItem>(
    itemView,
    null,
    factory,
    itemListener
) {

    override var items: List<OrderItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.item.code == n.item.code }
    }

}