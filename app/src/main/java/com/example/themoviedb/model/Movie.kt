package com.example.themoviedb.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results") val movies: List<Movie>
)

data class Movie(
    @SerializedName("title") val movieName: String,
    @SerializedName("poster_path") val posterPath: String
) {
    val url: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}