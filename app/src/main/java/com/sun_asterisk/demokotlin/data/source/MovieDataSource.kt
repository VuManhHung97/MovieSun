package com.sun_asterisk.demokotlin.data.source

import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.remote.responce.GenreResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.MovieResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.VideoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieDataSource {
    interface MovieLocalDataSource {
        fun getMovieLocal(): Flowable<List<Movie>>
        fun deleteMovie(movie: Movie)
        fun insertMovie(movie: Movie)
        fun isMovieFavorite(isMovie: Int): Flowable<Boolean>
    }

    interface MovieRemoteDataSource {
        fun getMovieBanner(page: Int): Observable<MovieResponse>
        fun getMoviePopular(page: Int): Observable<MovieResponse>
        fun getMovieComingSoon(page: Int): Observable<MovieResponse>
        fun getMovieNowPlaying(page: Int): Observable<MovieResponse>
        fun getMovieGenre(): Observable<GenreResponse>
        fun getMovieByGenre(withGenres: Int,page: Int): Observable<MovieResponse>
        fun getMovieDetail(id: Int): Observable<Movie>
        fun getMovieVideo(id: Int): Observable<VideoResponse>
        fun getMovieSearch(keyword: String, page: Int): Observable<MovieResponse>
    }
}
