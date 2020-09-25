package com.cabify.cabifystore.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    abstract fun bind(element: T, listener: OnItemSelectListener?, position:Int)
}