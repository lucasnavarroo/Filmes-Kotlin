package com.example.filmeskotlinteste.modules.movie.business

import com.example.filmeskotlinteste.modules.movie.database.MovieDatabase
import com.example.filmeskotlinteste.modules.movie.model.Movie
import com.example.filmeskotlinteste.modules.movie.model.MovieDetails
import com.example.filmeskotlinteste.modules.movie.network.MovieNetwork

object MovieBusiness {

    fun getMovies(
        page: Int,
        onSuccess: (movie: MutableList<Movie>) -> Unit,
        onError: (error: String, moviesFromDb: MutableList<Movie>) -> Unit
    ) {
        MovieNetwork.getMoviesFromAPI(
            page,
            onSuccess = { apiResponse ->
                apiResponse.let { moviesRes ->

                    if (page == 1) MovieDatabase.clear()

                    MovieDatabase.save(moviesRes)
                    val moviesDb = MovieDatabase.get()

                    onSuccess(moviesDb)
                }
            },
            onError = { error ->
                val moviesDb: MutableList<Movie> =
                    MovieDatabase.get()

                onError(error, moviesDb)
            }
        )
    }

    fun getMovie(
        movieId: Int,
        onSuccess: (movie: MovieDetails) -> Unit,
        onError: (error: String) -> Unit
    ) {
        MovieNetwork.getMovieDetailsFromAPI(movieId.toString(), onSuccess = {
            onSuccess(it)
        }, onError = {
            onError(it)
        })
    }

    fun searchMovies(
        query: String,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        MovieNetwork.searchMoviesFromAPI(query,onSuccess = {movies ->
            onSuccess(movies)
        }, onError = {errorMessage ->
            onError(errorMessage)
        })
    }
}