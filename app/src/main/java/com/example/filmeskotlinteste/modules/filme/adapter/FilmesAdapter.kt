package com.example.filmeskotlinteste.modules.filme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.filme.view.holder.FilmeViewHolder
import com.example.filmeskotlinteste.modules.filme.model.Movie

class FilmesAdapter(val clickListener: (idFilme: Int) -> Unit ) : RecyclerView.Adapter<FilmeViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filmes, parent, false)

        return FilmeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.bind(movies[position], clickListener)
    }

    fun refresh(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}