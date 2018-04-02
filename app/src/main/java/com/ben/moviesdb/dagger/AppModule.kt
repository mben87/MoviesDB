package com.ben.moviesdb.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.ben.moviesdb.MoviesApp
import com.ben.moviesdb.apicalls.ApiCallsManager
import com.ben.moviesdb.db.AppDatabase
import com.ben.moviesdb.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MoviesApp) {
    private val appDB = Room.databaseBuilder(application, AppDatabase::class.java, "main_app_db").build()

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideDatabase() = appDB

    @Provides
    @Singleton
    fun provideMovieRepository() = MovieRepository(ApiCallsManager(application), appDB.moviesDao())
}