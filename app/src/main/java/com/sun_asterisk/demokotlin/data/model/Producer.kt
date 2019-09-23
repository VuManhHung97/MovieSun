package com.sun_asterisk.demokotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Producer(
    val id: Float,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
) : Parcelable
