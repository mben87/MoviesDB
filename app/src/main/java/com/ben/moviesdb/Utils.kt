package com.ben.moviesdb

import android.content.Context

/**
 * Created by Ben on 02/04/2018.
 */
object Utils {
    fun getImageUrl(context: Context, posterPath: String) = "https://image.tmdb.org/t/p/w185${posterPath}?api_key=${context.getString(R.string.api_key)}"
}