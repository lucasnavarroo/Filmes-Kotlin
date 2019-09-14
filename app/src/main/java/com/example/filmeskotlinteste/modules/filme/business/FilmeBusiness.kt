package com.example.filmeskotlinteste.modules.filme.business

import com.example.filmeskotlinteste.modules.filme.database.FilmeDatabase
import com.example.filmeskotlinteste.modules.filme.model.Movie
import com.example.filmeskotlinteste.modules.filme.network.FilmeNetwork

object FilmeBusiness {

    fun getMovies(
        page: Int,
        onSuccess: (movie: MutableList<Movie>) -> Unit,
        onError: (error: String, movies: MutableList<Movie>) -> Unit
    ) {
        FilmeNetwork.requestFilmesFromAPI(
            page,
            onSuccess = { apiResponse ->
                apiResponse?.let { moviesRes ->

                    if(page == 1) FilmeDatabase.clear()

                    FilmeDatabase.save(moviesRes)
                    val moviesDb = FilmeDatabase.get()

                    onSuccess(moviesDb)
                }
            },
            onError = { error ->
                val filmesDb: MutableList<Movie> =
                    FilmeDatabase.get()

                onError(error, filmesDb)
            }
        )
    }
}