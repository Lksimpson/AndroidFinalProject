package com.example.finalproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.group27project1.api.*
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
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        TmdbApi = retrofit.create(tmdbApi::class.java)
    }

    fun getTrending(currentLatitude: Double, currentLongitude: Double): MutableLiveData<MovieItem> {
        val responseLiveData: MutableLiveData<MovieItem> = MutableLiveData()

//        val locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        val flickrRequest: Call<TrendingResponse> = TmdbApi.getTrending(currentLatitude.toString(), currentLongitude.toString())
        flickrRequest.enqueue(object : Callback<TrendingResponse> {
            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
            override fun onResponse(
                call: Call<TrendingResponse>,
                response: Response<TrendingResponse>
            ) {
                Log.d(TAG, "Response received")
                //responseLiveData.value = response.body()
                val trendingResponse: TrendingResponse? = response.body()
                var trendingMoviesList: List<MovieItem> = trendingResponse?.results?: mutableListOf()
//                entryResponse = entryResponse.filterNot {
//                  //  it.isBlank()
//                }
                //var weItem: MovieItem = entryResponse?.get(1).weItems

                responseLiveData.value = trendingMoviesList.get(1)
            }
        })
        return responseLiveData
    }

}