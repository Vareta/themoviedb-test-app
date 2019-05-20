package com.example.themoviedb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.themoviedb.viewModel.MoviesViewModel
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.activities.MovieDetail
import com.example.themoviedb.adapters.MoviesListAdapter
import com.example.themoviedb.model.Movie

class MainActivity : AppCompatActivity(), MoviesListAdapter.OnMovieListener {
    private var movies = mutableListOf<Movie>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MoviesViewModel
    private lateinit var retryButton: Button
    private lateinit var progress: ProgressBar
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Movies"

        recyclerView = findViewById(R.id.list)
        retryButton = findViewById(R.id.retryButton)
        progress = findViewById(R.id.progress)
        toolbar = findViewById(R.id.toolbar)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        setSupportActionBar(toolbar)

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = MoviesListAdapter(this@MainActivity, movies, this@MainActivity)
        }
        observeViewModel()
    }

    private fun observeViewModel(popular: Boolean = true) {
        val requiredMovies: LiveData<List<Movie>> = if (popular) viewModel.getMovies() else viewModel.getMovies(false)
        requiredMovies.observe(this, Observer { movies ->
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
                Toast.makeText(this, getString(R.string.movie_error), Toast.LENGTH_SHORT).show()
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
        val intent = Intent(baseContext, MovieDetail::class.java)
        intent.putExtra("movie", movies[position])
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.popular -> {
            observeViewModel()
            true
        }
        R.id.top_rated -> {
            observeViewModel(false)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}