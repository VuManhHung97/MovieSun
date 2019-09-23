package com.sun_asterisk.demokotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    var id: Int,
    var name: String?
) : Parcelable
