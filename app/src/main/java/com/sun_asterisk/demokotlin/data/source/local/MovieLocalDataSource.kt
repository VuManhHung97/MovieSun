package com.sun_asterisk.demokotlin.data.source.local

import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieDataSource
import io.reactivex.Flowable

class MovieLocalDataSource private constructor(private val movieDAO: MovieDAO) :
    MovieDataSource.MovieLocalDataSource {

    override fun isMovieFavorite(isMovie: Int): Flowable<Boolean> {
        return movieDAO.isMovieFavorite(isMovie)
    }

    override fun insertMovie(movie: Movie) {
        return movieDAO.insertMovie(movie)
    }

    override fun deleteMovie(movie: Movie) {
        return movieDAO.deleteMovie(movie)
    }

    override fun getMovieLocal(): Flowable<List<Movie>> {
        return movieDAO.getAllMovie()
    }

    companion object {
        private var sInstance: MovieLocalDataSource? = null

        @JvmStatic
        fun getInstance(movieDAO: MovieDAO): MovieLocalDataSource {
            synchronized(this) {
                if (sInstance == null) {
                    sInstance = MovieLocalDataSource(movieDAO)
                }
            }
            return sInstance!!
        }
    }
}
