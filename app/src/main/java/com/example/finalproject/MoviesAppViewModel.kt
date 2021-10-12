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
        //movieItemLiveData = tmdbFetchr().getTrending()
        movieItemLiveData = tmdbFetchr().getSearch("Star Wars")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

//    fun onSearch(query: String){
//        Log.d(TAG, "Search Made")
//        movieItemLiveData = tmdbFetchr().getSearch(query)
//    }

    private val favoritesRepository = FavoritesRepository.get()
    val favoritesListLiveData = favoritesRepository.getFavorites()
    val movieLiveData = MutableLiveData<MovieItem>()


    //val gameSummary = mutableListOf<Games>()


    //add methods here
//    val getCurrentAScore: Int
//        get() = curGame.teamAScore
//    val getCurrentBScore: Int
//        get() = curGame.teamBScore
//
//    fun setCurrentAScore(newScore: Int) {
//        curGame.teamAScore = newScore
//    }
//
//    fun setCurrentBScore(newScore: Int) {
//        curGame.teamBScore = newScore
//    }
    fun addFavorite(favorite: MovieItem) {
        favoritesRepository.addFavorite(favorite)
    }
    fun updateFavorite(favorite: MovieItem) {
        favoritesRepository.updateFavorite(favorite)
    }

    fun loadMovie(movieId: UUID, title:String, overview: String, rating: String, posterpath: String) {
        movieLiveData.value = MovieItem(movieId,title,overview,rating,posterpath)
//        movieDetailLiveData.value = movieId
//        movieDetailLiveData_title.value = title
//        movieDetailLiveData_overview.value = overview
//        movieDetailLiveData_rating.value = rating
//        movieDetailLiveData_posterpath.value = posterpath
    }
//,overview,rating,posterpath
//    fun getPhotoFile(game: Game): File {
//        return gameRepository.getPhotoFile(game)
//    }
}