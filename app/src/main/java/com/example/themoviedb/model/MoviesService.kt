package com.example.themoviedb.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MoviesService {
    private val api: MoviesApi
    private val appId = "34738023d27013e6d1b995443764da44"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create(MoviesApi::class.java)
    }

    fun getPopularMovies(): Single<Response> {
        return api.getPopularMovies(appId)
    }

    fun getTopRatedMovies(): Single<Response> {
        return api.getTopRatedMovies(appId)
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }
}