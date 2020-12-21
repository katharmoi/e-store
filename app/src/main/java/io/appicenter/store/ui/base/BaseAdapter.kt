package io.appicenter.store.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T : BaseViewHolder<R>, R> constructor(
    private val itemView: Int,
    private val emptyView: View?,
    private val factory: (view: View) -> T,
    private val itemListener: OnItemSelectListener?
) : RecyclerView.Adapter<T>() {

    private var context: Context? = null

    abstract var items: List<R>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        context = parent.context
        val v: View = LayoutInflater.from(parent.context).inflate(itemView, parent, false)
        return factory(v)
    }

    override fun getItemCount(): Int {
        emptyView?.visibility = if (items.isNotEmpty()) View.GONE else View.VISIBLE
        return items.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        if (items.isNotEmpty()) holder.bind(items[position], itemListener, position)
    }


    fun getContext(): Context? {
        return context
    }


    fun autoNotify(
        old: List<R>,
        new: List<R>,
        compare: (R, R) -> Boolean
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })

        diff.dispatchUpdatesTo(this)
    }

}