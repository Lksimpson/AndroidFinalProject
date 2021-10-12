package com.example.finalproject.api

import com.example.finalproject.MovieItem
import com.google.gson.annotations.SerializedName

class TrendingResponse {
    @SerializedName("results")
    lateinit var results:  List<MovieItem>

    //lateinit var city:  cityWeatherInfo
    //lateinit var count:


}