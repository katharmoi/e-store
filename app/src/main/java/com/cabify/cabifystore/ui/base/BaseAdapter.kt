package com.cabify.cabifystore.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val VIEW_TYPE_EMPTY = 0
private const val VIEW_TYPE_OBJECT = 1

abstract class BaseAdapter<T : BaseViewHolder<R>, R> constructor(
    private val items: MutableList<R>,
    private val itemView: Int,
    private val emptyView: Int,
    private val factory: (view: View) -> T,
    private val itemListener: OnItemSelectListener?
) : RecyclerView.Adapter<T>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        context = parent.context
        val v: View = when (viewType) {
            VIEW_TYPE_OBJECT -> LayoutInflater.from(parent.context).inflate(itemView, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(emptyView, parent, false)
        }

        return factory(v)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        if (items.isNotEmpty()) holder.bind(items[position], itemListener, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_OBJECT
    }

    fun getContext(): Context? {
        return context
    }

}