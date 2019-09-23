package com.sun_asterisk.demokotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String?,
    val key: String?,
    val name: String?,
    val size: Float
) : Parcelable
