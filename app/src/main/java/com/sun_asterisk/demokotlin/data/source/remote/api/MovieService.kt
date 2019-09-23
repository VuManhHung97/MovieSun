package com.sun_asterisk.demokotlin.data.source.remote.api

import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.remote.responce.GenreResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.MovieResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.VideoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated?language=en")
    fun getMovieBanner(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/popular?language=en")
    fun getMoviePopular(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/upcoming?language=en")
    fun getMovieComingSoon(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/now_playing?language=en")
    fun getMovieNowPlaying(@Query("page") page: Int): Observable<MovieResponse>

    @GET("genre/movie/list?en-US&language=en-US")
    fun getMovieGenre(): Observable<GenreResponse>

    @GET("discover/movie?&language=en-US")
    fun getMovieByGenre(@Query("with_genres") withGenres: Int, @Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetail(@Path("movie_id") id: Int): Observable<Movie>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(@Path("movie_id") id: Int): Observable<VideoResponse>

    @GET("search/multi?language=en-US")
    fun getMovieSearch(@Query("query") keyword: String, @Query("page") page: Int): Observable<MovieResponse>
}
