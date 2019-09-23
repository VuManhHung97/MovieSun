package com.sun_asterisk.demokotlin.screen.moviebygenre.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.utils.BaseAdapter
import com.sun_asterisk.demokotlin.utils.BaseViewHolder
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_movie_by_genre.view.*

class MovieByGenreAdapter : BaseAdapter<Movie>() {
  override fun viewHolder(view: View): BaseViewHolder<Movie> = MovieByGenreViewHolder(view)

  override fun layout(row: Int): Int = R.layout.item_movie_by_genre

  companion object {
    class MovieByGenreViewHolder(
        private val view: View
    ) : BaseViewHolder<Movie>(view) {
      override fun bindData(data: Movie, listener: OnItemRecyclerViewClickListener<Movie>) {
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w500" + data.backdropPath).into(
            view.imageBackdropPath)
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w342" + data.posterPath).into(
            view.imagePosterPath)
        view.textViewTitle.text = data.title
        view.textVoteAverage.text = data.voteAverage.toString()
        view.textReleaseDate.text = data.releaseDate
        view.ratingBarMovie.rating = data.voteAverageSeparate()
        view.setOnClickListener { listener.onItemClick(data) }
      }
    }
  }
}