package com.sun_asterisk.demokotlin.data.source.remote

import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieDataSource
import com.sun_asterisk.demokotlin.data.source.remote.api.MovieService
import com.sun_asterisk.demokotlin.data.source.remote.api.ServiceGenerator
import com.sun_asterisk.demokotlin.data.source.remote.responce.GenreResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.MovieResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.VideoResponse
import io.reactivex.Observable

class MovieRemoteDataSource private constructor(private val movieService: MovieService) :
    MovieDataSource.MovieRemoteDataSource {

    override fun getMovieSearch(keyword: String, page: Int): Observable<MovieResponse> {
        return movieService.getMovieSearch(keyword,page)
    }

    override fun getMovieVideo(id: Int): Observable<VideoResponse> {
        return movieService.getMovieVideos(id)
    }

    override fun getMovieDetail(id: Int): Observable<Movie> {
        return movieService.getMovieDetail(id)
    }

    override fun getMovieByGenre(withGenres: Int,page: Int): Observable<MovieResponse> {
        return movieService.getMovieByGenre(withGenres, page)
    }

    override fun getMovieGenre(): Observable<GenreResponse> {
        return movieService.getMovieGenre()
    }

    override fun getMovieNowPlaying(page: Int): Observable<MovieResponse> {
        return movieService.getMovieNowPlaying(page)
    }

    override fun getMovieComingSoon(page: Int): Observable<MovieResponse> {
        return movieService.getMovieComingSoon(page)
    }

    override fun getMoviePopular(page: Int): Observable<MovieResponse> {
        return movieService.getMoviePopular(page)
    }

    override fun getMovieBanner(page: Int): Observable<MovieResponse> {
        return movieService.getMovieBanner(page)
    }

    companion object {
        private var sInstance = MovieRemoteDataSource(ServiceGenerator.create())

        fun getInstance(): MovieRemoteDataSource {
            return sInstance
        }
    }
}
