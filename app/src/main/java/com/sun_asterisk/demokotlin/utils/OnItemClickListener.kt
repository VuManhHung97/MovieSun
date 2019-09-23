package com.sun_asterisk.demokotlin.utils

import com.sun_asterisk.demokotlin.data.model.Movie

interface OnItemClickListener {
    fun onItemRecyclerViewClick(movie: Movie)
}