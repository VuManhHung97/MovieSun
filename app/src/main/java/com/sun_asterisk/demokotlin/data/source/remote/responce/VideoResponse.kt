package com.sun_asterisk.demokotlin.data.source.remote.responce

import com.google.gson.annotations.SerializedName
import com.sun_asterisk.demokotlin.data.model.Video

class VideoResponse {
    @SerializedName("results")
    var videos: List<Video>? = null
}
