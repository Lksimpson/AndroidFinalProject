package com.example.finalproject.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface tmdbApi {

    @GET(
        "3/trending/movie/day?api_key=165a3064b3775600958191f699fc1a99"
                //"&lat=42.2743&lon=-71.8081"

    )
    fun getTrending(): Call<TrendingResponse>
    //fun fetchPhotos(): Call<WeatherResponse>

    @GET
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
}

//api key 165a3064b3775600958191f699fc1a99