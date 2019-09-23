package com.sun_asterisk.demokotlin.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id: Int,
    var title: String?,
    @SerializedName("vote_average")
    var voteAverage: Float,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    var popularity: Float,
    var runtime: Long,
    var genres: MutableList<Genre>? = mutableListOf(),
    @SerializedName("production_companies")
    val productionCompanies: MutableList<Producer>? = mutableListOf()
) : Parcelable {
    fun voteAverageSeparate(): Float = voteAverage / 2
    fun getPosterPathLink(): String = "https://image.tmdb.org/t/p/w780$posterPath"
}
