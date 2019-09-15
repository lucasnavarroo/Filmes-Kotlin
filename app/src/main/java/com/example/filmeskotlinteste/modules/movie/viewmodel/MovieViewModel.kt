package com.example.filmeskotlinteste.modules.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmeskotlinteste.core.livedata.SingleLiveEvent
import com.example.filmeskotlinteste.modules.movie.business.MovieBusiness
import com.example.filmeskotlinteste.modules.movie.model.Movie

class MovieViewModel : ViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    val onLoadFinished = SingleLiveEvent<Void>()
    val onLoadMoreStarted = SingleLiveEvent<Void>()

    val onError = SingleLiveEvent<String>()

    fun requestFilmes(page: Int) {

        if (page > 1) onLoadMoreStarted.call()

        MovieBusiness.getMovies(
            page,
            onSuccess = { moviesRes ->
                movies.value = moviesRes
                onLoadFinished.call()
            }, onError = { error, moviesDb ->
                onError.value = error

                movies.value = moviesDb
                onLoadFinished.call()
            })
    }
}