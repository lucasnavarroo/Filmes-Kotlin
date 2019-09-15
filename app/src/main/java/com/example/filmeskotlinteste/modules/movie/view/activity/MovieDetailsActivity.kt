package com.example.filmeskotlinteste.modules.movie.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.movie.view.activity.MoviesActivity.Companion.ID_MOVIE
import com.example.filmeskotlinteste.modules.movie.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_detalhes_filme.*

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)

        subscribeUI()

        configBackClick()
    }

    override fun onResume() {
        super.onResume()

        val movieId = intent.getIntExtra(ID_MOVIE, 0)

        if (movieId != 0) {
            movieDetailsViewModel.requestMovie(movieId)
        }
    }


    private fun subscribeUI() {
        with(movieDetailsViewModel) {

            onLoadFinished.observe(this@MovieDetailsActivity, Observer {
                progressBarMovieDetails.visibility = GONE
            })

            onError.observe(this@MovieDetailsActivity, Observer { errorMessage ->
                Log.d("MOVIES-ERROR", errorMessage)
            })

            movie.observe(this@MovieDetailsActivity, Observer { movieDetails ->
                textViewDetailsMovieTitle.text = movieDetails.title
                textViewDetailsMovieTagline.text = movieDetails.tagline
                textViewDetailsMovieYear.text = movieDetails.releaseDate
                textViewDetailsMovieOverview.text = movieDetails.overview
                textViewRevenue.text = " - revenue: U$${movieDetails.revenue.toString()}"

                ratingbarDetailsMovie.rating = (movieDetails.voteAverage!! / 2)

                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
                    .placeholder(R.drawable.imdb_fb_logo)
                    .override(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    )
                    .into(imageViewBackgroundMovie)
            })
        }
    }

    private fun configBackClick() {
        imageViewBack.setOnClickListener { finish() }
    }
}
