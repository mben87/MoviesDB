package com.ben.moviesdb.movieslist

import com.ben.moviesdb.entities.Movie

interface MoviesListViewCallbacks {

    fun showMoviesList(movies: List<Movie>)
    fun showLoading(show: Boolean)
    fun onError(msg: String)
}