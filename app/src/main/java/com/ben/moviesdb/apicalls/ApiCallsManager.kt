package com.ben.moviesdb.apicalls

import android.content.Context
import com.ben.moviesdb.Consts
import com.ben.moviesdb.R
import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.entities.Trailer
import com.koushikdutta.ion.Ion
import io.reactivex.Observable
import org.json.JSONArray
import org.json.JSONObject

class ApiCallsManager(private val context: Context) : MoviesApi {
    override fun getMovies(): Observable<List<Movie>> {
        return Observable.create {
            Ion.with(context)
                    .load("https://api.themoviedb.org/3/movie/popular?api_key=${context.getString(R.string.api_key)}&language=en-US&sort_by=popularity.desc")
                    .asString()
                    .setCallback { e, result ->
                        if (e == null) {
                            val moviesArray: JSONArray = JSONObject(result).getJSONArray("results")

                            val moviesList = mutableListOf<Movie>()

                            for (i in 0 until moviesArray.length()) {
                                with(moviesArray.getJSONObject(i)) {
                                    moviesList.add(
                                            Movie(uid = getLong(Consts.ID),
                                                    title = getString(Consts.TITLE),
                                                    overview = getString(Consts.OVERVIEW),
                                                    posterPath = getString(Consts.POSTER_PATH),
                                                    voteAverage = getString(Consts.VOTE_AVERAGE),
                                                    date = getString(Consts.DATE)
                                            )
                                    )
                                }
                            }

                            it.onNext(moviesList)
                            it.onComplete()
                        } else {
                            it.onError(e)
                        }
                    }
        }
    }

    override fun getMovieDetails(uid: Long): Observable<Movie> {
        return Observable.create {
            Ion.with(context)
                    .load("https://api.themoviedb.org/3/movie/$uid?api_key=${context.getString(R.string.api_key)}&language=en-US")
                    .asString()
                    .setCallback { e, result ->

                        if (e == null) {
                            with(JSONObject(result)) {
                                val movie = Movie(uid = getLong(Consts.ID),
                                        title = getString(Consts.TITLE),
                                        overview = getString(Consts.OVERVIEW),
                                        posterPath = getString(Consts.POSTER_PATH),
                                        voteAverage = getString(Consts.VOTE_AVERAGE),
                                        date = getString(Consts.DATE))
                                it.onNext(movie)
                            }

                            it.onComplete()
                        } else {
                            it.onError(e)
                        }
                    }
        }
    }

    override fun getMovieTrailers(uid: Long): Observable<List<Trailer>> {
        return Observable.create {
            Ion.with(context)
                    .load("https://api.themoviedb.org/3/movie/$uid/videos?api_key=${context.getString(R.string.api_key)}&language=en-US")
                    .asString()
                    .setCallback { e, result ->

                        if (e == null) {
                            val trailersArray: JSONArray = JSONObject(result).getJSONArray("results")

                            val moviesList = mutableListOf<Trailer>()

                            for (i in 0 until trailersArray.length()) {
                                with(trailersArray.getJSONObject(i)) {
                                    moviesList.add(
                                            Trailer(uid = getString(Consts.KEY),
                                                    name = getString(Consts.NAME),
                                                    type = getString(Consts.TYPE),
                                                    site = getString(Consts.SITE))
                                    )
                                }
                            }

                            it.onNext(moviesList)
                            it.onComplete()
                        } else {
                            it.onError(e)
                        }
                    }
        }
    }
}
