package com.example.finalproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.MainActivity
import com.example.finalproject.MovieItem

import java.util.*

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME)
    fun getFavorites(): LiveData<List<MovieItem>>

    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME+" WHERE db_id=(:db_id)")
    fun getFavorite(db_id: UUID): LiveData<MovieItem?>

    @Update
    fun updateFavorite(favorite: MovieItem)

    @Insert
    fun addFavorite(favorite: MovieItem)
}