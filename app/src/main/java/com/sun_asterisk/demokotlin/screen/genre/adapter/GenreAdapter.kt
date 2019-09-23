package com.sun_asterisk.demokotlin.screen.genre.adapter

import android.view.View
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Genre
import com.sun_asterisk.demokotlin.utils.BaseAdapter
import com.sun_asterisk.demokotlin.utils.BaseViewHolder
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter : BaseAdapter<Genre>() {
    override fun layout(row: Int): Int = R.layout.item_genre

    override fun viewHolder(view: View): BaseViewHolder<Genre> = GenreAdapterViewHolder(view)

    companion object {
        class GenreAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Genre>(view) {
            override fun bindData(data: Genre, listener: OnItemRecyclerViewClickListener<Genre>) {
                view.textViewTitleGenre.text = data.name
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}