package com.sun_asterisk.demokotlin.data.source

import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.data.source.remote.responce.GenreResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.MovieResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.VideoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

class MovieRepository private constructor(
    private val local: MovieLocalDataSource,
    private val remote: MovieRemoteDataSource
) {

    companion object {
        private var sInstance: MovieRepository? = null

        @JvmStatic
        fun instance(
            local: MovieLocalDataSource,
            remote: MovieRemoteDataSource
        ): MovieRepository {
            if (sInstance == null) {
                synchronized(this) {
                    sInstance = MovieRepository(local, remote)
                }
            }
            return sInstance!!
        }
    }

    fun getMovieBanner(page: Int): Observable<MovieResponse> {
        return remote.getMovieBanner(page)
    }

    fun getMovieComingSoon(page: Int): Observable<MovieResponse> {
        return remote.getMovieComingSoon(page)
    }

    fun getMoviePopular(page: Int): Observable<MovieResponse> {
        return remote.getMoviePopular(page)
    }

    fun getMovieNowPlaying(page: Int): Observable<MovieResponse> {
        return remote.getMovieNowPlaying(page)
    }

    fun getMovieGenre(): Observable<GenreResponse> {
        return remote.getMovieGenre()
    }

    fun getMovieByGenre(withGenres: Int, page: Int): Observable<MovieResponse> {
        return remote.getMovieByGenre(withGenres, page)
    }

    fun getMovieDetail(id: Int): Observable<Movie> {
        return remote.getMovieDetail(id)
    }

    fun getMovieVideo(id: Int): Observable<VideoResponse> {
        return remote.getMovieVideo(id)
    }

    fun getMovieSearch(keyword: String, page: Int): Observable<MovieResponse> {
        return remote.getMovieSearch(keyword, page)
    }

    fun getMovieLocal(): Flowable<List<Movie>> {
        return local.getMovieLocal()
    }

    fun isMovieFavorite(id: Int): Flowable<Boolean> {
        return local.isMovieFavorite(id)
    }

    fun deleteMovie(movie: Movie) {
        return local.deleteMovie(movie)
    }

    fun insertMovie(movie: Movie) {
        return local.insertMovie(movie)
    }
}
