package com.ben.moviesdb.moviedetails

import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.entities.Trailer

interface MovieDetailsViewCallbacks {

    fun showMovieDetails(movie: Movie)
    fun showMovieTrailers(trailers: List<Trailer>)
    fun showLoading(show: Boolean)
    fun onError(msg: String)
}