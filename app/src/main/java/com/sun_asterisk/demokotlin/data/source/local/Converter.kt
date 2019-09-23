package com.sun_asterisk.demokotlin.data.source.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sun_asterisk.demokotlin.data.model.Genre
import com.sun_asterisk.demokotlin.data.model.Producer

open class Converter {
    @TypeConverter
    fun fromStringToGenreList(value: String): List<Genre>? =
        Gson().fromJson<List<Genre>>(value, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromStringToProducerList(value: String): List<Producer>? =
        Gson().fromJson<List<Producer>>(value, object : TypeToken<List<Producer>>() {}.type)

    @TypeConverter
    fun fromListGenreToString(list: List<Genre>?): String = Gson().toJson(list)

    @TypeConverter
    fun fromListProducerToString(list: List<Producer>?): String = Gson().toJson(list)
}
