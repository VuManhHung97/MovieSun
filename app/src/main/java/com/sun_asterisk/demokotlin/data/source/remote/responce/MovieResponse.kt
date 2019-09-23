package com.sun_asterisk.demokotlin.data.source.remote.responce

import com.google.gson.annotations.SerializedName
import com.sun_asterisk.demokotlin.data.model.Movie

class MovieResponse {
    @SerializedName("results")
    var movies: List<Movie>? = null
}
