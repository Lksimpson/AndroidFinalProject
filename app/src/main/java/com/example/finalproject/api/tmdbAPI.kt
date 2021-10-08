package com.example.group27project1.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tmdbApi {

    @GET(
        "data/2.5/forecast?id=524901"+
                "&appid=cdab7026197127a8a9654afad9de122d"+
                "&exclude=minutely,hourly,daily,alerts" +
                "&units=metric"
                //"&lat=42.2743&lon=-71.8081"

    )
    fun getTrending(@Query("lat") lat: String?, @Query("lon") lon: String?): Call<TrendingResponse>
    //fun fetchPhotos(): Call<WeatherResponse>
}

//api key 165a3064b3775600958191f699fc1a99