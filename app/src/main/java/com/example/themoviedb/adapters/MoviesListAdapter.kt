package com.example.themoviedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.themoviedb.R
import com.example.themoviedb.model.Movie

class MoviesListAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private var mOnMovieListener: OnMovieListener
) :
    RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup, onMovieListener: OnMovieListener) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_list_item, parent, false)), View.OnClickListener {
        private val onMovieListener: OnMovieListener
        private var title: TextView? = null
        var posterImage: ImageView

        init {
            title = itemView.findViewById(R.id.movieName)
            posterImage = itemView.findViewById(R.id.posterImage)
            this.onMovieListener = onMovieListener
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            title?.text = movie.movieName
        }

        override fun onClick(v: View) {
            onMovieListener.onMovieClick(v, adapterPosition)
        }
    }

    interface OnMovieListener {
        fun onMovieClick(v: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent, mOnMovieListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies[position]
        Glide.with(context).load(movies[position].url).apply(RequestOptions.overrideOf(200, 300))
            .apply(RequestOptions.centerCropTransform()).into(holder.posterImage)

        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

}