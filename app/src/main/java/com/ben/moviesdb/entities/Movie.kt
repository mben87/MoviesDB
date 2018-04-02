package com.ben.moviesdb.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(@PrimaryKey val uid: Long, val title: String, val overview: String,
                 val voteAverage: String, val posterPath: String, val date: String,
                 val trailers: List<Trailer> = emptyList()) {
}