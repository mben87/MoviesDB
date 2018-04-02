package com.ben.moviesdb

import android.app.Application
import com.ben.moviesdb.dagger.AppModule
import com.ben.moviesdb.dagger.ApplicationComponent
import com.ben.moviesdb.dagger.DaggerApplicationComponent

class MoviesApp : Application() {

    companion object {
        @JvmStatic
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)

    }
}