package com.cabify.cabifystore.ui.cart

import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.base.BaseViewHolder
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.OrderItem
import kotlinx.android.synthetic.main.list_item_cart.view.*

class CartViewHolder(itemView: View) : BaseViewHolder<OrderItem>(itemView) {
    override fun bind(element: OrderItem, listener: OnItemSelectListener?, position: Int) {
        itemView.apply {
            cart_item_name.text = element.item.name
            cart_item_qty.text = itemView.context.getString(
                R.string.qty_template,
                element.count
            )

            val totalText = itemView.context.getString(
                R.string.price_template,
                "€",
                element.item.price.times(element.count.toBigDecimal())
            )

            val discountedTotalText = if (element.discount != null) {
                itemView.context.getString(
                    R.string.price_template,
                    "€",
                    element.item.price.times(element.count.toBigDecimal()).minus(element.discount!!)
                )
            } else ""

            cart_item_total.apply {
                text = totalText
                if (element.discount != null) paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            cart_item_discounted_total.text = discountedTotalText

            val image = when (element.item.code) {
                "TSHIRT" -> ContextCompat.getDrawable(context, R.drawable.tshirt)
                "VOUCHER" -> ContextCompat.getDrawable(context, R.drawable.voucher)
                "MUG" -> ContextCompat.getDrawable(context, R.drawable.mug)
                else -> ContextCompat.getDrawable(context, R.drawable.img_plant_1)
            }

            cart_img_product.setImageDrawable(image)
        }
    }
}