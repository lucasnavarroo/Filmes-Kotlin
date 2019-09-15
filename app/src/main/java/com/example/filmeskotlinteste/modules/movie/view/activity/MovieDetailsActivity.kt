package com.example.filmeskotlinteste.modules.movie.view.activity

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.movie.database.MovieDatabase
import com.example.filmeskotlinteste.modules.movie.model.Movie
import com.example.filmeskotlinteste.modules.movie.view.activity.MoviesActivity.Companion.ID_MOVIE
import kotlinx.android.synthetic.main.activity_detalhes_filme.*

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)

        getMovieFromDb()

        bind()

        configBackClick()
    }

    private fun getMovieFromDb() {
        val movieId = intent.getIntExtra(ID_MOVIE, 0)

        if (movieId != 0) {
            movie = MovieDatabase.getMovieById(movieId)!!
        }
    }

    private fun bind() {
        textViewDetailsMovieTitle.text = movie.title
        textViewDetailsMovieTagline.text = movie.tagline
        textViewDetailsMovieYear.text = movie.releaseDate
        textViewDetailsMovieOverview.text = movie.overview
        textViewRevenue.text = " / U$${movie.revenue.toString()}"

        ratingbarDetailsMovie.rating = (movie.voteAverage!! / 2)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .override(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            )
            .into(imageViewBackgroundMovie)
    }

    private fun configBackClick() {
        imageViewBack.setOnClickListener { finish() }
    }
}
