package io.appicenter.store.ui.cart

import android.view.View
import io.appicenter.domain.model.OrderItem
import io.appicenter.store.ui.base.BaseAdapter
import io.appicenter.store.ui.base.OnItemSelectListener
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