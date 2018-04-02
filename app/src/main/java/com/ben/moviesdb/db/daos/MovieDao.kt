package com.ben.moviesdb.db.daos

import android.arch.persistence.room.*
import com.ben.moviesdb.entities.Movie
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies ORDER BY uid")
    fun getAllMovies(): Single<List<Movie>>

    @Query("SELECT * FROM movies WHERE uid = :uid")
    fun getMoviesByUid(uid: Long): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movie: List<Movie>)

    @Delete
    fun remove(movie: Movie)
}