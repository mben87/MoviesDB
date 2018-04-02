package com.ben.moviesdb.moviedetails


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.ben.moviesdb.Consts
import com.ben.moviesdb.R
import com.ben.moviesdb.Utils
import com.ben.moviesdb.entities.Movie
import com.ben.moviesdb.entities.Trailer
import com.ben.moviesdb.managers.YouTubeManager
import com.ben.moviesdb.movieslist.ui.TrailersListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.jetbrains.anko.bundleOf


class MovieDetailsFragment : Fragment(), MovieDetailsViewCallbacks {

    private lateinit var presenter: MovieDetailsPresenter

    var uid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            uid = this[Consts.UID] as Long
        }

        presenter = MovieDetailsPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getMovieDetails(uid)
    }

    // presenter's callbacks
    override fun showMovieDetails(movie: Movie) {

        val path = Utils.getImageUrl(context!!, movie.posterPath)

        Picasso.get().load(path).into(fragment_movies_details_poster_img)

        fragment_movies_details_title_tv.text = movie.title
        fragment_movies_details_year_tv.text = movie.date.substring(0, movie.date.indexOf("-"))
        fragment_movies_details_overview_tv.text = movie.overview

        val vote = "${movie.voteAverage}/10"
        fragment_movies_details_vote_tv.text = vote

        initShowMore()
    }

    private fun initShowMore() {

        if (fragment_movies_details_overview_tv.lineCount > 2) {
            fragment_movies_details_show_more_btn.visibility = VISIBLE

            fragment_movies_details_show_more_btn.setOnClickListener {
                if (fragment_movies_details_overview_tv.maxLines == 2) {
                    fragment_movies_details_overview_tv.maxLines = 100
                    fragment_movies_details_show_more_btn.text = getString(R.string.show_less)
                } else {

                    fragment_movies_details_overview_tv.maxLines = 2
                    fragment_movies_details_show_more_btn.text = getString(R.string.show_more)
                }
            }
        } else {
            fragment_movies_details_show_more_btn.visibility = INVISIBLE
        }
    }

    override fun showMovieTrailers(trailers: List<Trailer>) {
        fragment_movies_details_trailers_rv.layoutManager = LinearLayoutManager(context)
        fragment_movies_details_trailers_rv.adapter = TrailersListAdapter(trailers, { trailer ->
            YouTubeManager.watchYoutubeVideo(context!!, trailer.uid)
        })
    }

    override fun showLoading(show: Boolean) {
        fragment_movies_details_trailers_progressBar.visibility = if (show) VISIBLE else GONE
    }

    override fun onError(msg: String) {
        Log.d("showMovieDetails", "onerror = $msg")

    }

    // end presenter's callbacks


    fun changeMovieDetails(uid: Long) {
        fragment_movies_details_trailers_rv.adapter = null
        presenter.getMovieDetails(uid)
    }

    companion object {
        @JvmStatic
        fun newInstance(uid: Long): MovieDetailsFragment =
                MovieDetailsFragment().apply {
                    arguments = bundleOf(Pair(Consts.UID, uid))
                }
    }
}
