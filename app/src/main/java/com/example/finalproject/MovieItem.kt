package com.example.finalproject

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = MainActivity.TABLE_NAME)
data class MovieItem(
    //@SerializedName("id")
    @PrimaryKey
    var db_id: UUID = UUID.randomUUID(),
    @SerializedName("id")
    var tmdb_id: Int = 0,
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var poster_path: String = "",
    var release_date: String = "",
    var title: String = "",
    var video: String = "",
    var vote_average: Double = 0.0,
    var vote_count: Int = 0,
    var popularity: Double = 0.0,
    var media_type: String = "",
)
