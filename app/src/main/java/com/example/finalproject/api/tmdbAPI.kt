package com.example.finalproject.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface tmdbApi {

    @GET(
        "3/trending/movie/day?api_key=165a3064b3775600958191f699fc1a99"
    )
    fun getTrending(): Call<TrendingResponse>

    @GET(
        "3/search/movie?api_key=165a3064b3775600958191f699fc1a99&language=en-US&page=1&include_adult=false"
    )
    fun getSearch(@Query("query") query: String?): Call<SearchResponse>

    @GET
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
}
