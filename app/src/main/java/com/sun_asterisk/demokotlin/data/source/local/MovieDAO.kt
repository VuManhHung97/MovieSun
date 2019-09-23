package com.sun_asterisk.demokotlin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sun_asterisk.demokotlin.data.model.Movie
import io.reactivex.Flowable

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movies")
    fun getAllMovie(): Flowable<List<Movie>>

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM movies WHERE id = :idMovie) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    fun isMovieFavorite(idMovie: Int): Flowable<Boolean>

    @Delete
    fun deleteMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)
}
