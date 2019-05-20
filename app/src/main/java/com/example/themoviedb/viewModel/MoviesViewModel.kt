package com.example.themoviedb.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MoviesService
import com.example.themoviedb.model.Response
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MoviesViewModel : ViewModel() {
    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val movieError: MutableLiveData<Boolean> = MutableLiveData()
    private val service: MoviesService = MoviesService()

    fun getMovies(popular: Boolean = true): LiveData<List<Movie>> {
        if (popular) fetchMovies(true) else fetchMovies(false)
        return movies
    }

    fun getMovieError(): LiveData<Boolean> {
        return movieError
    }

    private fun fetchMovies(popular: Boolean) {
        val initFetch: Single<Response> = if (popular) service.getPopularMovies() else service.getTopRatedMovies()
        initFetch
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response>() {
                override fun onSuccess(value: Response) {
                    movies.value = value.movies
                    movieError.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d("ERROR", e.toString())
                    movieError.value = true
                }
            })
    }

    fun onRefresh() {
        fetchMovies(true)
    }

}

