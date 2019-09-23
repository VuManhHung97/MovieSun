package com.sun_asterisk.demokotlin.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val mMovieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DiscoverViewMode::class.java -> DiscoverViewMode(mMovieRepository) as T
            else -> super.create(modelClass)
        }
    }
}
