package com.example.group27project1.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.MovieItem

@Database(entities = [ MovieItem::class ], version=1, exportSchema = false)
@TypeConverters(GameTypeConverters::class)

abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun movieDao(): FavoriteDAO
}
