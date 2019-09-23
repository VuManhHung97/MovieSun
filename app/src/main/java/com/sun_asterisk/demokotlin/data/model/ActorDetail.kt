package com.sun_asterisk.demokotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActorDetail(
    var id: Float,
    var name: String?,
    @SerializedName("known_for_department")
    var knownFor: String?,
    var gender: Float,
    var biography: String?,
    @SerializedName("place_of_birth")
    var placeOfBirth: String?,
    @SerializedName("profile_path")
    var profilePath: String?,
    var birthday: String?
) : Parcelable
