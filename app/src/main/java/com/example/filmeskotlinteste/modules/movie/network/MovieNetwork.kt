package com.example.filmeskotlinteste.modules.movie.network

import com.example.filmeskotlinteste.core.network.BaseNetwork
import com.example.filmeskotlinteste.modules.movie.model.Movie
import com.example.filmeskotlinteste.modules.movie.model.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object MovieNetwork : BaseNetwork() {

    private val API by lazy { getRetrofitBuilder().build().create(FilmeAPI::class.java) }

    var getMovies: Disposable? = null
    var getMovie: Disposable? = null
    var searchMovie: Disposable? = null

    fun getMoviesFromAPI(
        page: Int,
        onSuccess: (response: MutableList<Movie>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        getMovies?.dispose()

        getMovies = API.getMovies(page.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                movies.results?.let { onSuccess(it) }
            }, {
                onError(it.message.toString())
            })
    }

    fun getMovieDetailsFromAPI(
        movieId: String,
        onSuccess: (response: MovieDetails) -> Unit,
        onError: (error: String) -> Unit
    ) {
        getMovie?.dispose()

        getMovie = API.getMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                movie?.let { onSuccess(it) }
            }, {
                onError(it.message.toString())
            })
    }

    fun searchMoviesFromAPI(
        query: String,
        onSuccess: (response: MutableList<Movie>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        searchMovie?.dispose()

        searchMovie = API.searchMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                movies.results?.let { onSuccess(it) }
            }, {
                onError(it.message.toString())
            })
    }
}