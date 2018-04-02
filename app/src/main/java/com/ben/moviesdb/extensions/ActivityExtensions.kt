package com.ben.moviesdb.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).addToBackStack(null).commit()
}
