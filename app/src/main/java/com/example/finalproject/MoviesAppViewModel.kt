package com.example.finalproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

private const val TAG = "MoviesAppViewModel"

class MoviesAppViewModel : ViewModel(){

    val movieItemLiveData: LiveData<List<MovieItem>>

    init {
        Log.d(TAG, "ViewModel instance created")
        movieItemLiveData = tmdbFetchr().getTrending()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    private val favoritesRepository = FavoritesRepository.get()
    val favoritesListLiveData = favoritesRepository.getFavorites()
    private val movieDetailLiveData = MutableLiveData<UUID>()

    //val curMovie = MovieItem()
    //store team scores here
    //var isScoreSaved = false


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

    fun loadMovie(movieId: UUID) {
        movieDetailLiveData.value = movieId
    }
//    fun getPhotoFile(game: Game): File {
//        return gameRepository.getPhotoFile(game)
//    }
}