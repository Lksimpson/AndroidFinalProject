package com.example.finalproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.io.File

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
    //private val gameRepository = GameRepository.get()
    //val gameListLiveData = gameRepository.getGames()



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
//    fun addGame(game: Game) {
//        gameRepository.addGame(game)
//    }
//    fun updateGame(game: Game) {
//        gameRepository.updateGame(game)
//    }
//    fun getPhotoFile(game: Game): File {
//        return gameRepository.getPhotoFile(game)
//    }
}