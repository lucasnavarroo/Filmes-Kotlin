package com.example.filmeskotlinteste.modules.filme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmeskotlinteste.core.livedata.SingleLiveEvent
import com.example.filmeskotlinteste.modules.filme.business.FilmeBusiness
import com.example.filmeskotlinteste.modules.filme.model.Movie

class FilmeViewModel : ViewModel() {

    var filmes: MutableLiveData<List<Movie>> = MutableLiveData()

    val onLoadStarted = SingleLiveEvent<Void>()
    val onLoadFinished = SingleLiveEvent<Void>()
    val onError = SingleLiveEvent<String>()

    fun requestFilmes() {

        onLoadStarted.call()

        FilmeBusiness.getMovies(onSuccess = { filmesRes ->
            filmes.value = filmesRes
            onLoadFinished.call()
        }, onError = {error, filmesDb ->
            onError.value = error

            filmes.value = filmesDb
            onLoadFinished.call()
        })
    }
}