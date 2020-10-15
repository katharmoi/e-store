package com.cabify.cabifystore.ui.cart

import android.view.View
import com.cabify.cabifystore.ui.base.BaseAdapter
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.OrderItem
import kotlin.properties.Delegates


class CartAdapter(
    itemView: Int,
    emptyView: View?,
    factory: (view: View) -> CartViewHolder,
    val itemListener: OnItemSelectListener?
) : BaseAdapter<CartViewHolder, OrderItem>(
    itemView,
    emptyView,
    factory,
    itemListener
) {

    override var items: List<OrderItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.item.code == n.item.code }
    }

}