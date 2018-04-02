package com.ben.moviesdb.movieslist.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ben.moviesdb.R
import com.ben.moviesdb.entities.Trailer
import kotlinx.android.synthetic.main.item_list_trailer.view.*

class TrailersListAdapter(private val trailers: List<Trailer>, private val itemClick: (Trailer) -> Unit) : RecyclerView.Adapter<TrailersListAdapter.TrailersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder = TrailersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_trailer, parent, false), itemClick)

    override fun getItemCount() = trailers.size

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        holder.bind(trailers[position])
    }


    class TrailersViewHolder(view: View, private val itemClick: (Trailer) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(item: Trailer) = with(itemView) {

            item_list_trailer_title_tv.text = item.name
            item_list_trailer_type_tv.text = item.type
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}