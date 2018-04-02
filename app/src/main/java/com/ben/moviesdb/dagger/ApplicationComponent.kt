package com.ben.moviesdb.dagger

import com.ben.moviesdb.MoviesApp
import com.ben.moviesdb.moviedetails.MovieDetailsPresenter
import com.ben.moviesdb.movieslist.MoviesListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent {
    fun inject(application: MoviesApp)
    fun inject(moviesListPresenter: MoviesListPresenter)
    fun inject(movieDetailsPresenter: MovieDetailsPresenter)

}