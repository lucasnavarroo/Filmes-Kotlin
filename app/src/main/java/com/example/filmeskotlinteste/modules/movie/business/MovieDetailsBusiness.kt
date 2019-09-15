package com.example.filmeskotlinteste.modules.movie.business

import com.example.filmeskotlinteste.modules.movie.database.MovieDetailsDatabase
import com.example.filmeskotlinteste.modules.movie.model.MovieDetails
import com.example.filmeskotlinteste.modules.movie.network.MovieNetwork

object MovieDetailsBusiness {

    fun getMovieDetails(
        movieId: Int,
        onSuccess: (movie: MovieDetails) -> Unit,
        onError: (error: String, movieDb: MovieDetails) -> Unit
    ) {
        MovieNetwork.getMovieDetailsFromAPI(movieId.toString(), onSuccess = {
            MovieDetailsDatabase.save(it)
            onSuccess(it)
        }, onError = {
            val movieDetailsDb = MovieDetailsDatabase.get()
            onError(it, movieDetailsDb)
        })
    }
}