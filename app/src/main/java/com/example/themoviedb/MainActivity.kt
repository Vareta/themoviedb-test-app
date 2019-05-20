package com.example.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.themoviedb.viewModel.MoviesViewModel
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.adapters.MoviesListAdapter
import com.example.themoviedb.model.Movie

class MainActivity : AppCompatActivity(), MoviesListAdapter.OnMovieListener {
    private var movies = mutableListOf<Movie>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MoviesViewModel
    private lateinit var retryButton: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Movies"

        recyclerView = findViewById(R.id.list)
        retryButton = findViewById(R.id.retryButton)
        progress = findViewById(R.id.progress)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = MoviesListAdapter(this@MainActivity, movies, this@MainActivity)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getMovies().observe(this, Observer { movies ->
            if (movies != null) {
                this.movies.clear()
                this.movies.addAll(movies)
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter?.notifyDataSetChanged()
            } else {
                recyclerView.visibility = View.GONE
            }

        })

        viewModel.getMovieError().observe(this, Observer { error ->
            progress.visibility = View.GONE
            if (error) {
                Toast.makeText(this, "Error consiguiendo peliculas", Toast.LENGTH_SHORT).show()
                retryButton.visibility = View.VISIBLE
            } else {
                retryButton.visibility = View.GONE
            }
        })
    }

    fun onRetry(view: View) {
        viewModel.onRefresh()
        recyclerView.visibility = View.GONE
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    override fun onMovieClick(v: View, position: Int) {
        Toast.makeText(this, movies[position].movieName, Toast.LENGTH_SHORT).show()
    }
}
