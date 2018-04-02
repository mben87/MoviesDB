package com.ben.moviesdb.moviedetails

import com.ben.moviesdb.MoviesApp
import com.ben.moviesdb.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsPresenter(private val callbacks: MovieDetailsViewCallbacks) {

    @Inject
    lateinit var movieRep: MovieRepository

    init {
        MoviesApp.component.inject(this)

    }

    fun getMovieDetails(uid: Long): Disposable {
        return movieRep.getMovieDetails(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->

                    if (movie.trailers.isEmpty()) {
                        callbacks.showLoading(true)

                        getMovieTrailers(uid)

                    } else {
                        callbacks.showMovieTrailers(movie.trailers)
                    }

                    callbacks.showMovieDetails(movie)

                }, {
                    callbacks.onError(it.message!!)
                })
    }

    private fun getMovieTrailers(uid: Long) {
        movieRep.getMovieTrailers(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ trailers ->
                    callbacks.showMovieTrailers(trailers)

                }, {
                    callbacks.onError("error occurred while trying to show trailers")
                }, {
                    callbacks.showLoading(false)
                })
    }
}