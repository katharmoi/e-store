package io.appicenter.store.ui.orders

import android.view.View
import io.appicenter.store.R
import io.appicenter.domain.model.Order
import io.appicenter.store.ui.base.BaseViewHolder
import io.appicenter.store.ui.base.OnItemSelectListener
import kotlinx.android.synthetic.main.list_item_order.view.*
import java.text.SimpleDateFormat
import java.util.*

class OrdersViewHolder(itemView: View) : BaseViewHolder<Order>(itemView) {
    override fun bind(element: Order, listener: OnItemSelectListener?, position: Int) {
        val itemAdapter = OrderItemAdapter(
            R.layout.list_item_cart,
            { v: View -> OrderItemViewHolder(v) },
            null
        )

        itemView.apply {
            order_item_recycler.apply {
                adapter = itemAdapter
            }

            val dateFormat = "dd/MM/yyyy hh:mm"
            val sdf = SimpleDateFormat(dateFormat, Locale.US)

            order_id.text = element.id.toString()
            order_date.text = sdf.format(element.date!!)
            order_total.text =
                context.getString(R.string.total_after_discounts_template, "€", element.total)
        }

        itemAdapter.items = element.items
    }
}