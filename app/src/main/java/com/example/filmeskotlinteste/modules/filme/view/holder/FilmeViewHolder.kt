package com.example.filmeskotlinteste.modules.filme.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.filmeskotlinteste.modules.filme.model.Movie
import kotlinx.android.synthetic.main.item_filmes.view.*

class FilmeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie, clickListener: (idFilme: Int) -> Unit) {
        with(view) {

            setOnClickListener {
                movie.id?.let { filmeId -> clickListener(filmeId) }
            }

            textViewTituloFilme.text = movie.title
            
        }
    }
}
