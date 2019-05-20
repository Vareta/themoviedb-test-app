package com.example.themoviedb.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.model.Movie

class MovieDetail : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var posterImage : ImageView
    private lateinit var movieName : TextView
    private lateinit var voteAverage : TextView
    private lateinit var voteCount : TextView
    private lateinit var popularity : TextView
    private lateinit var releaseDate : TextView
    private lateinit var overview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val movie : Movie = intent.getSerializableExtra("movie") as Movie
        title = movie.movieName
        setUpElements(movie)
        setSupportActionBar(toolbar)
    }

    fun setUpElements(movie : Movie){
        toolbar = findViewById(R.id.toolbar)
        posterImage = findViewById(R.id.posterImage)
        movieName = findViewById(R.id.title)
        voteAverage = findViewById(R.id.voteAverage)
        popularity = findViewById(R.id.popularity)
        releaseDate = findViewById(R.id.releaseDate)
        overview = findViewById(R.id.overview)

        movieName.text = movie.movieName
        voteAverage.text = getString(R.string.average_vote, movie.voteAverage, movie.voteCount)
        popularity.text = getString(R.string.popularity, movie.popularity)
        releaseDate.text = getString(R.string.release_date, movie.releaseDate)
        overview.text = movie.overview
        Glide.with(this).load(movie.url).into(posterImage)
    }
}
