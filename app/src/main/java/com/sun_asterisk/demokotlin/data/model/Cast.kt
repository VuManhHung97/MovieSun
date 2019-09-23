package com.sun_asterisk.demokotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    var id: Int,
    var name: String?,
    @SerializedName("profile_path")
    var profilePath: String?,
    var gender: Float,
    @SerializedName("credit_id")
    var creditId: Float,
    @SerializedName("cast_id")
    var castId: Float
) : Parcelable