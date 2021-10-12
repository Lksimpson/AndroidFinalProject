package com.example.finalproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

private const val TAG = "MoviesAppViewModel"

class MoviesAppViewModel : ViewModel(){


    var movieItemLiveData: LiveData<List<MovieItem>>

    init {
        Log.d(TAG, "ViewModel instance created")
        movieItemLiveData = tmdbFetchr().getTrending()
        //movieItemLiveData = tmdbFetchr().getSearch("Star Wars")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }



    private val favoritesRepository = FavoritesRepository.get()
    val favoritesListLiveData = favoritesRepository.getFavorites()
    val movieLiveData = MutableLiveData<MovieItem>()

    fun addFavorite(favorite: MovieItem) {
        favoritesRepository.addFavorite(favorite)
    }
    fun updateFavorite(favorite: MovieItem) {
        favoritesRepository.updateFavorite(favorite)
    }
    fun deleteFavorite(favorite: MovieItem) {
        favoritesRepository.removeFavorite(favorite)
    }

    fun loadMovie(movieId: UUID, title:String, overview: String, rating: String, posterpath: String) {
        movieLiveData.value = MovieItem(movieId,title,overview,rating,posterpath)
    }


    fun getPhotoFile(movie: MovieItem): File {
        return favoritesRepository.getPhotoFile(movie)
    }
}