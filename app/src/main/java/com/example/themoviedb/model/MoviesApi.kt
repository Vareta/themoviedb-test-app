package com.example.themoviedb.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("popular")
    fun getPopularMovies(@Query("api_key") appId: String): Single<Response>

    @GET("top_rated")
    fun getTopRatedMovies(@Query("api_key") app_id: String): Single<Response>
}