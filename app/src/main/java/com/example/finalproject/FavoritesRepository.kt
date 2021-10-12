package com.example.finalproject

//import GameDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.finalproject.database.FavoritesDatabase
import java.io.File
import java.util.*
import java.util.concurrent.Executors

    private const val DATABASE_NAME = "favorites_database.db"

class FavoritesRepository private constructor(context: Context) {

    private val database : FavoritesDatabase = Room.databaseBuilder(
        context.applicationContext,
        FavoritesDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val gameDao = database.movieDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir


    fun getFavorites(): LiveData<List<MovieItem>> = gameDao.getFavorites()

    fun getFavorite(id: UUID): LiveData<MovieItem?> = gameDao.getFavorite(id)

    fun updateFavorite(movie: MovieItem) {
        executor.execute {
            gameDao.updateFavorite(movie)
        }
    }

    fun addFavorite(movie: MovieItem) {
        executor.execute {
            gameDao.addFavorite(movie)
        }
    }
    fun removeFavorite(movie: MovieItem) {
        executor.execute {
            gameDao.removeFavorite(movie)
        }
    }

    fun getPhotoFile(movie: MovieItem): File = File(filesDir, movie.photoFileName)


    companion object {
        private var INSTANCE: FavoritesRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FavoritesRepository(context)
            }
        }
        fun get(): FavoritesRepository {
            return INSTANCE ?:
            throw IllegalStateException("GameRepository must be initialized")
        }
    }

}