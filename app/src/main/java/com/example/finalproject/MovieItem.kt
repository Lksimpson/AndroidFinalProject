package com.example.finalproject

data class MovieItem(
    var id: Int = 0,
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
