package com.example.filmeskotlinteste.modules.movie.business

import com.example.filmeskotlinteste.modules.movie.database.MovieDatabase
import com.example.filmeskotlinteste.modules.movie.model.Movie
import com.example.filmeskotlinteste.modules.movie.network.MovieNetwork

object MovieBusiness {

    fun getMovies(
        page: Int,
        onSuccess: (movie: MutableList<Movie>) -> Unit,
        onError: (error: String, movies: MutableList<Movie>) -> Unit
    ) {
        MovieNetwork.requestFilmesFromAPI(
            page,
            onSuccess = { apiResponse ->
                apiResponse.let { moviesRes ->

                    if(page == 1) MovieDatabase.clear()

                    MovieDatabase.save(moviesRes)
                    val moviesDb = MovieDatabase.get()

                    onSuccess(moviesDb)
                }
            },
            onError = { error ->
                val filmesDb: MutableList<Movie> =
                    MovieDatabase.get()

                onError(error, filmesDb)
            }
        )
    }
}