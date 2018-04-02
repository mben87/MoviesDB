package com.ben.moviesdb.movieslist.ui


import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.ben.moviesdb.activities.MainActivity
import com.ben.moviesdb.R
import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.movieslist.MoviesListPresenter
import com.ben.moviesdb.movieslist.MoviesListViewCallbacks
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton


class MoviesListFragment : Fragment(), MoviesListViewCallbacks, LifecycleOwner {
    private val presenter = MoviesListPresenter(this)
    var isTablet = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        isTablet = (activity as MainActivity).isTablet
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(presenter)

        presenter.getMovies()
    }

    // presenter's callbacks
    override fun showMoviesList(movies: List<Movie>) {
        fragment_movies_list_RV.layoutManager = GridLayoutManager(activity!!.applicationContext, if (isTablet) 3 else 2)
        fragment_movies_list_RV.adapter = MoviesListAdapter(context!!, movies, { movie ->

            if (activity != null && !activity!!.isFinishing && activity is MainActivity) {
                (activity as MainActivity).onMovieSelected(movie.uid)
            }
        })

        if (isTablet) {
            (activity as MainActivity).onMovieSelected(movies[0].uid)
        }

    }

    override fun showLoading(show: Boolean) {
        fragment_movies_list_progressBar.visibility = if (show) VISIBLE else GONE

    }

    override fun onError(msg: String) {
        alert("Try again?", "Error occurred") {
            yesButton {
                presenter.getMovies()
            }
            noButton {}

        }.show()
    }

    // end presenter's callbacks

}
