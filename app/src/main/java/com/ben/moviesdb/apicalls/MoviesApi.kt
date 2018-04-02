package com.ben.moviesdb.apicalls

import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.entities.Trailer
import io.reactivex.Observable

interface MoviesApi {

    fun getMovies(): Observable<List<Movie>>

    fun getMovieDetails(uid: Long): Observable<Movie>

    fun getMovieTrailers(uid: Long): Observable<List<Trailer>>
}