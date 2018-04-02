package com.ben.moviesdb.movieslist.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ben.moviesdb.R
import com.ben.moviesdb.Utils
import com.ben.moviesdb.entities.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_movie.view.*

class MoviesListAdapter(val context: Context, val movies: List<Movie>, val itemClick: (Movie) -> Unit) : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder = MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false), itemClick)

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }


    class MoviesViewHolder(view: View, private val itemClick: (Movie) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(item: Movie) = with(itemView) {
            val path = Utils.getImageUrl(context, item.posterPath)

            Picasso.get().load(path).into(item_list_movie_img)

            itemView.setOnClickListener { itemClick(item) }
        }
    }
}