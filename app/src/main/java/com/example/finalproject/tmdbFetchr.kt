package com.example.finalproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.api.TrendingResponse
import com.example.finalproject.api.tmdbApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "tmdbFetchr"

class tmdbFetchr {

    private val TmdbApi: tmdbApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        TmdbApi = retrofit.create(tmdbApi::class.java)
    }

    fun getTrending(): LiveData<List<MovieItem>> {
        val responseLiveData: MutableLiveData<List<MovieItem>> = MutableLiveData()
        val tmdbRequest: Call<TrendingResponse> = TmdbApi.getTrending()

        tmdbRequest.enqueue(object : Callback<TrendingResponse> {
            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
            override fun onResponse(call: Call<TrendingResponse>, response: Response<TrendingResponse>) {
                Log.d(TAG, "Response received")
                val trendingResponse: TrendingResponse? = response.body()
                var trendingMoviesList: List<MovieItem> = trendingResponse?.results?: mutableListOf()

                trendingMoviesList = trendingMoviesList.filterNot {
                    it.poster_path.isNullOrBlank()
                }

                responseLiveData.value = trendingMoviesList
            }
        })

        return responseLiveData
    }

    @WorkerThread
    fun fetchPhoto(url: String): Bitmap? {
        val response: Response<ResponseBody> = TmdbApi.fetchUrlBytes(url).execute()
        val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
        Log.i(TAG, "Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }

}