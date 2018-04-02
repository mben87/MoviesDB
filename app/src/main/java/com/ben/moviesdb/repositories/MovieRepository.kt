package com.ben.moviesdb.repositories

import android.util.Log
import com.ben.moviesdb.apicalls.MoviesApi
import com.ben.moviesdb.db.daos.MoviesDao
import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.entities.Trailer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieRepository(private val moviesApi: MoviesApi, private val moviesDao: MoviesDao) {

    fun getMovies(): Observable<List<Movie>> =
            Observable.concatArray(
                    getMoviesFromDb(),
                    getMoviesFromApi())


    private fun getMoviesFromDb(): Observable<List<Movie>> {
        return moviesDao.getAllMovies().filter { it.isNotEmpty() }
                .toObservable()
    }

    private fun getMoviesFromApi(): Observable<List<Movie>> {
        return moviesApi.getMovies()
                .doOnNext {

                    storeMoviesInDb(it)
                }
    }

    private fun storeMoviesInDb(movies: List<Movie>) {
        Observable.fromCallable { moviesDao.insertAll(movies) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("storeMovieInDb", "inserted ${movies.size} movies")
                }
    }

    fun getMovieDetails(uid: Long): Observable<Movie> = Observable.concatArray(
            getMovieDetailsFromDb(uid)
    )

    private fun getMovieDetailsFromDb(uid: Long): Observable<Movie> {
        return moviesDao.getMoviesByUid(uid).filter { it.isNotEmpty() }
                .toObservable()
                .flatMap { list -> Observable.just(list[0]) }
    }

    fun getMovieTrailers(uid: Long): Observable<List<Trailer>> = getTrailersFromApi(uid)

    private fun getTrailersFromApi(uid: Long): Observable<List<Trailer>> {
        return moviesApi.getMovieTrailers(uid)
    }
}