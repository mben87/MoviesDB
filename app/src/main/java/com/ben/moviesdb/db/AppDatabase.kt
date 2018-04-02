package com.ben.moviesdb.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.ben.moviesdb.db.converters.DbConverters
import com.ben.moviesdb.db.daos.MoviesDao
import com.ben.moviesdb.entities.Movie

@Database(entities = [(Movie::class)], version = 2, exportSchema = false)

@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}