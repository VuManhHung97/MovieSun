package com.sun_asterisk.demokotlin.utils

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.data.model.Movie

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    private lateinit var mListener: OnItemRecyclerViewClickListener<T>
    private var data = mutableListOf<T>()
    fun setOnItemClickListener(listener: OnItemRecyclerViewClickListener<T>) {
        mListener = listener
    }

    fun replaceItem(item: List<T>) {
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        data.add(item)
        notifyItemChanged(data.indexOf(item))
    }

    fun addItems(item: List<T>) {
        val originSize = data.size
        data.addAll(item)
        notifyItemRangeInserted(originSize, item.size)
    }

    fun removeItem(item: T) {
        if (item in data) {
            val positionItem = data.indexOf(item)
            data.removeAt(positionItem)
            notifyItemRemoved(positionItem)
        }
    }

    fun clearList() {
        data.clear()
        notifyDataSetChanged()
    }

    protected abstract fun layout(row: Int): Int

    protected abstract fun viewHolder(view: View): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BaseViewHolder<T> {
        return viewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = layout(position)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindData(data[position],mListener)
    }
}
