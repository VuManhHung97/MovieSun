package com.sun_asterisk.demokotlin.data.source.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase.Companion.DATABASE_VERSION

@Database(entities = [Movie::class], version = DATABASE_VERSION)
@TypeConverters(value = [Converter::class])
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

    companion object {
        private var sMovieDatabase: MovieDatabase? = null
        internal const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "room_database_movies"

        fun getInstance(context: Context): MovieDatabase {
            if (sMovieDatabase == null) {
                sMovieDatabase = Room.databaseBuilder(context, MovieDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sMovieDatabase as MovieDatabase
        }
    }
}
