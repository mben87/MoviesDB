package com.ben.moviesdb.db.converters

import android.arch.persistence.room.TypeConverter
import com.ben.moviesdb.entities.Trailer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbConverters {

    @TypeConverter
    fun fromString(value: String): List<Trailer> {
        val listType = object : TypeToken<List<Trailer>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Trailer>): String {
        val gson = Gson()
        return gson.toJson(list)

    }
}