package com.ben.moviesdb.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ben.moviesdb.R
import com.ben.moviesdb.extensions.replaceFragment
import com.ben.moviesdb.moviedetails.MovieDetailsFragment
import com.ben.moviesdb.movieslist.ui.MoviesListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var isTablet = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container?.run {
            isTablet = false
            showMoviesList()
        }
    }

    private fun showMoviesList() {
        title = getString(R.string.pop_movies)
        replaceFragment(MoviesListFragment())
    }

    private fun showMovieDetails(id: Long) {

        replaceFragment(MovieDetailsFragment.newInstance(id))
    }

    fun onMovieSelected(id: Long) {
        if (activity_main_movie_details_fragment == null) {
            title = getString(R.string.movie_details)
            showMovieDetails(id)

        } else {
            (supportFragmentManager
                    .findFragmentById(R.id.activity_main_movie_details_fragment) as MovieDetailsFragment)
                    .changeMovieDetails(id)

        }
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 1) {
            title = getString(R.string.pop_movies)
            supportFragmentManager.popBackStack()

        } else {
            super.onBackPressed()

        }
    }
}
