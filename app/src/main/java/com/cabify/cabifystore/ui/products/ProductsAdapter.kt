package com.cabify.cabifystore.ui.products

import android.view.View
import com.cabify.cabifystore.ui.base.BaseAdapter
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.OrderItem
import kotlin.properties.Delegates


class ProductsAdapter(
    itemView: Int,
    emptyView: View?,
    factory: (view: View) -> ProductsViewHolder,
    val itemListener: OnItemSelectListener?
) : BaseAdapter<ProductsViewHolder, OrderItem>(
    itemView,
    emptyView,
    factory,
    itemListener
) {

    override var items: List<OrderItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.item.code == n.item.code }
    }

}