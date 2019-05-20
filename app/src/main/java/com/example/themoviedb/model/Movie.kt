package com.example.themoviedb.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Response(
    @SerializedName("results") val movies: List<Movie>
)

data class Movie(
    @SerializedName("title") val movieName: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val voteAverage: String,
    @SerializedName("vote_count") val voteCount: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String
) : Serializable {
    val url: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}