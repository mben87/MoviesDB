package com.ben.moviesdb.movieslist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.ben.moviesdb.MoviesApp
import com.ben.moviesdb.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesListPresenter(private val callbacks: MoviesListViewCallbacks) : LifecycleObserver {

    private var getMovieDisposable: Disposable? = null

    @Inject
    lateinit var movieRep: MovieRepository

    init {
        MoviesApp.component.inject(this)
    }

    fun getMovies() {
        callbacks.showLoading(true)

        getMovieDisposable = movieRep.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callbacks.showMoviesList(it)

                }, {
                    Log.e("MoviesListPresenter", "getMovies msg = ${it.message}")
                    callbacks.onError(it.message!!)
                }, {
                    callbacks.showLoading(false)
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        getMovieDisposable?.dispose()
    }
}