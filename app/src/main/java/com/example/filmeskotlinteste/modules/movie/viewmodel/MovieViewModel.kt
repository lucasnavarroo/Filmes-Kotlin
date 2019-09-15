package com.example.filmeskotlinteste.modules.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmeskotlinteste.core.livedata.SingleLiveEvent
import com.example.filmeskotlinteste.modules.movie.business.MovieBusiness
import com.example.filmeskotlinteste.modules.movie.model.Movie

class MovieViewModel : ViewModel() {

    var filmes: MutableLiveData<List<Movie>> = MutableLiveData()

    val onLoadStarted = SingleLiveEvent<Void>()
    val onLoadFinished = SingleLiveEvent<Void>()
    val onLoadMoreStarted = SingleLiveEvent<Void>()

    val onError = SingleLiveEvent<String>()

    fun requestFilmes(page: Int) {

        if(page == 1) onLoadStarted.call()
        else onLoadMoreStarted.call()

        MovieBusiness.getMovies(
            page,
            onSuccess = { filmesRes ->
                filmes.value = filmesRes
                onLoadFinished.call()
            }, onError = { error, filmesDb ->
                onError.value = error

                filmes.value = filmesDb
                onLoadFinished.call()
            })
    }
}