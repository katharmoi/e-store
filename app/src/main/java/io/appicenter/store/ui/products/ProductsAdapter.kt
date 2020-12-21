package io.appicenter.store.ui.products

import android.view.View
import io.appicenter.domain.model.OrderItem
import io.appicenter.store.ui.base.BaseAdapter
import io.appicenter.store.ui.base.OnItemSelectListener
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