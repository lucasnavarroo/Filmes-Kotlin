package com.example.filmeskotlinteste.modules.filme.view.holder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmeskotlinteste.modules.filme.model.Movie
import kotlinx.android.synthetic.main.item_filmes.view.*

class FilmeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie, clickListener: (idFilme: Int) -> Unit) {
        with(view) {

            setOnClickListener {
                movie.id?.let { movieId -> clickListener(movieId) }
            }

            textViewTituloFilme.text = movie.title
            textViewAnoFilme.text = movie.releaseDate
            ratingbarMovie.rating = (movie.voteAverage!! / 2)

            Glide.with(view)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .override(74, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)
                .fitCenter()
                .into(imageViewPosterFilme)
        }
    }
}