package com.cabify.cabifystore.ui.products

import android.view.View
import androidx.core.content.ContextCompat
import com.cabify.cabifystore.R
import com.cabify.cabifystore.ui.base.BaseViewHolder
import com.cabify.cabifystore.ui.base.OnItemSelectListener
import com.cabify.domain.model.OrderItem
import kotlinx.android.synthetic.main.list_item_product.view.*
import java.math.BigDecimal

class ProductsViewHolder(itemView: View, private val viewModel: MainActivityViewModel) :
    BaseViewHolder<OrderItem>(itemView) {

    override fun bind(element: OrderItem, listener: OnItemSelectListener?, position: Int) {
        with(itemView) {
            item_name.text = element.item.name
            val applicableDiscountVisibility =
                if (element.discount != null && element.discount!! > BigDecimal.ZERO) {
                    View.VISIBLE
                } else View.INVISIBLE

            item_desc.apply {
                visibility = applicableDiscountVisibility
                text = context.getString(
                    R.string.applied_discount_template,
                    element.applicableDiscount
                )
            }

            item_price.text = context.getString(R.string.price_template, "€", element.item.price)

            item_count.text = element.count.toString()

            val image = when (element.item.code) {
                "TSHIRT" -> ContextCompat.getDrawable(context, R.drawable.tshirt)
                "VOUCHER" -> ContextCompat.getDrawable(context, R.drawable.voucher)
                "MUG" -> ContextCompat.getDrawable(context, R.drawable.mug)
                else -> ContextCompat.getDrawable(context, R.drawable.img_plant_1)
            }
            img_product.setImageDrawable(image)
            btn_item_add.setOnClickListener { viewModel.addToCart(element.item) }
            btn_item_remove.setOnClickListener { viewModel.removeFromCart(element.item) }

            //Add stroke to the card if item is in cart
            if (element.count > 0) {
                item_card.strokeWidth = 4
                item_card.strokeColor = ContextCompat.getColor(context, R.color.btnColor)
            } else {
                item_card.strokeWidth = 0
            }
        }
    }
}