package com.example.filmeskotlinteste.modules.filme.business

import com.example.filmeskotlinteste.modules.filme.database.FilmeDatabase
import com.example.filmeskotlinteste.modules.filme.model.Movie
import com.example.filmeskotlinteste.modules.filme.network.FilmeNetwork

object FilmeBusiness {

    fun getMovies(
        onSuccess: (movie: MutableList<Movie>) -> Unit,
        onError: (error: String, movies: MutableList<Movie>) -> Unit
    ) {
        FilmeNetwork.requestFilmesFromAPI(
            onSuccess = { apiResponse ->
                apiResponse?.let { filmesRes ->

                    FilmeDatabase.clear()
                    FilmeDatabase.save(filmesRes)

                    onSuccess(filmesRes)
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