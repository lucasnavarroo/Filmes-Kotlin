package com.example.filmeskotlinteste.modules.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.movie.view.holder.MovieViewHolder
import com.example.filmeskotlinteste.modules.movie.model.Movie

class MoviesAdapter(val clickListener: (idFilme: Int) -> Unit ) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filmes, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], clickListener)
    }

    fun refresh(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}