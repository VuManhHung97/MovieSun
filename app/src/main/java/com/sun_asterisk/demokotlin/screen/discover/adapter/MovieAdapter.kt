package com.sun_asterisk.demokotlin.screen.discover.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.utils.BaseAdapter
import com.sun_asterisk.demokotlin.utils.BaseViewHolder
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : BaseAdapter<Movie>() {
    override fun layout(row: Int): Int = R.layout.item_movie

    override fun viewHolder(view: View): BaseViewHolder<Movie> =
        MovieAdapterViewHolder(view)

    companion object {
        class MovieAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Movie>(view) {
            override fun bindData(data: Movie, listener: OnItemRecyclerViewClickListener<Movie>) {
                view.textTitleAllSong.text = data.title
                view.textVoteAverage.text = data.voteAverage.toString()
                view.ratingBarMovie.rating = data.voteAverageSeparate()
                Glide.with(view.context).load("https://image.tmdb.org/t/p/w342" + data.posterPath)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
                    .into(view.imageViewAllSong)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}