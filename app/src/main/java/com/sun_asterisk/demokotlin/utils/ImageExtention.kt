    package com.sun_asterisk.demokotlin.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadCircleImageUrl(url: String) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadRadiusImageUrl(url: String, radius: Int) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
        .into(this)
}
