package io.appicenter.store.ui.orders

import android.view.View
import io.appicenter.domain.model.OrderItem
import io.appicenter.store.ui.base.BaseAdapter
import io.appicenter.store.ui.base.OnItemSelectListener
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