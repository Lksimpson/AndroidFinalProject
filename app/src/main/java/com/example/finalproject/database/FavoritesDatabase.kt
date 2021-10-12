package com.example.finalproject.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.MovieItem

@Database(entities = [ MovieItem::class ], version=1, exportSchema = false)
@TypeConverters(MovieTypeConverters::class)

abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun movieDao(): FavoriteDAO
}
