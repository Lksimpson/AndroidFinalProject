package com.example.finalproject

import android.app.Application

class MovieApp : Application(){

    override fun onCreate() {
        super.onCreate()
        FavoritesRepository.initialize(this)
    }
}