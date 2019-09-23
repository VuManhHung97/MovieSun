package com.sun_asterisk.demokotlin.screen.favorite.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.utils.BaseAdapter
import com.sun_asterisk.demokotlin.utils.BaseViewHolder
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.list_item_main.view.imageMovie
import kotlinx.android.synthetic.main.list_item_main.view.info_button
import kotlinx.android.synthetic.main.list_item_main.view.meal_tv

class MainListAdapter : BaseAdapter<Movie>() {
    override fun layout(row: Int): Int = R.layout.list_item_main

    override fun viewHolder(view: View): BaseViewHolder<Movie> = MainListAdapterViewHolder(view)

    companion object {
        class MainListAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Movie>(view) {

            override fun bindData(data: Movie, listener: OnItemRecyclerViewClickListener<Movie>) {
                Glide.with(view.context).load("https://image.tmdb.org/t/p/w342" + data.posterPath)
                    .into(view.imageMovie)
                view.meal_tv.text = data.title
                view.info_button.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}