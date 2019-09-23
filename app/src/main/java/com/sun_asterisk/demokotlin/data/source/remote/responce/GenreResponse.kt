package com.sun_asterisk.demokotlin.data.source.remote.responce

import com.google.gson.annotations.SerializedName
import com.sun_asterisk.demokotlin.data.model.Genre

class GenreResponse {
    @SerializedName("genres")
    var genres: List<Genre>? = null
}
