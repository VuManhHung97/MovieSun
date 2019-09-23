package com.sun_asterisk.demokotlin.screen.discover

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.remote.responce.GenreResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.MovieResponse
import com.sun_asterisk.demokotlin.data.source.remote.responce.VideoResponse
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DiscoverViewMode(private val movieRepository: MovieRepository) : ViewModel() {
    val movieResponseLive = MutableLiveData<MovieResponse>()
    val moviePopularLive = MutableLiveData<MovieResponse>()
    val movieComingSoonLive = MutableLiveData<MovieResponse>()
    val movieNowPlayingLive = MutableLiveData<MovieResponse>()
    val movieGenreLive = MutableLiveData<GenreResponse>()
    val movieByGenreLive = MutableLiveData<MovieResponse>()
    val movieDetailLive = MutableLiveData<Movie>()
    val movieVideoLive = MutableLiveData<VideoResponse>()
    val movieLocalLive = MutableLiveData<List<Movie>>()
    val isMovieFavoriteLive = MutableLiveData<Boolean>()
    var errorLiveData = MutableLiveData<Throwable>()
    val movieSearchLive = MutableLiveData<List<Movie>>()
    private val compositeDisposable = CompositeDisposable()

    fun init(page: Int) {
        compositeDisposable.add(
            movieRepository.getMovieBanner(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    data.let { movieResponseLive.value = it }
                }, { error ->
                     error.let { errorLiveData.value = it }
                })
        )
        compositeDisposable.add(
            movieRepository.getMoviePopular(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    data.let { moviePopularLive.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
        compositeDisposable.add(
            movieRepository.getMovieComingSoon(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    data.let { movieComingSoonLive.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
        compositeDisposable.add(
            movieRepository.getMovieNowPlaying(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    data.let { movieNowPlayingLive.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }

    fun initGenre() {
        compositeDisposable.add(
            movieRepository.getMovieGenre()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    data.let { movieGenreLive.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }

    fun initMovieDetail(id: Int) {
        compositeDisposable.add(
            movieRepository.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieDetailLive.value = it }, this::handleError)
        )
        compositeDisposable.add(
            movieRepository.getMovieVideo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieVideoLive.value = it }, this::handleError)
        )
        compositeDisposable.add(
            movieRepository.isMovieFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isMovieFavoriteLive.value = it }, this::handleError)
        )
    }

    fun initMovieByGenre(withGenres: Int, page: Int) {
        compositeDisposable.add(
            movieRepository.getMovieByGenre(withGenres, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieByGenreLive.value = it }, this::handleError)
        )
    }

    fun initMovieLocal() {
        compositeDisposable.add(
            movieRepository.getMovieLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieLocalLive.value = it }, this::handleError)
        )
    }

    fun addFavorite(movie: Movie) {
        if (isMovieFavoriteLive.value!!) removeMovieLocal(movie) else addMovieLocal(movie)
    }

    private fun addMovieLocal(movie: Movie) {
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<Any> { e ->
                movieRepository.insertMovie(movie = movie)
                e.onComplete()
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, this::handleError)
        )
    }

    fun removeMovieLocal(movie: Movie) {
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<Any> { e ->
                movieRepository.deleteMovie(movie = movie)
                e.onComplete()
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, this::handleError)
        )
    }

    fun initMovieSearch(keyword: String, page: Int) {
        compositeDisposable.add(
            movieRepository.getMovieSearch(keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { it.movies }
                .filter { it.title != null }
                .toList()
                .subscribe({ movieSearchLive.value = it }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.d("AAA", error.message)
    }

    fun getMovieGenre(): LiveData<GenreResponse> {
        return movieGenreLive
    }

    fun getMovieByGenre(): LiveData<MovieResponse> {
        return movieByGenreLive
    }

    fun getMovieDetail(): LiveData<Movie> {
        return movieDetailLive
    }

    fun getMovieVideo(): LiveData<VideoResponse> {
        return movieVideoLive
    }

    fun getMovieSearch(): LiveData<List<Movie>> {
        return movieSearchLive
    }

    fun getMovieLocal(): LiveData<List<Movie>> {
        return movieLocalLive
    }

    fun isMovieFavorite(): LiveData<Boolean> {
        return isMovieFavoriteLive
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
