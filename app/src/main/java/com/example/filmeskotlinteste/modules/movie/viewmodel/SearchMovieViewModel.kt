package com.example.filmeskotlinteste.modules.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmeskotlinteste.core.livedata.SingleLiveEvent
import com.example.filmeskotlinteste.modules.movie.business.MovieBusiness
import com.example.filmeskotlinteste.modules.movie.model.Movie

class SearchMovieViewModel : ViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    val onLoadFinished = SingleLiveEvent<Void>()
    val onLoadStarted = SingleLiveEvent<Void>()
    val onEmptySearch = SingleLiveEvent<Void>()

    val onError = SingleLiveEvent<String>()

    fun searchMovies(query: String) {

        onLoadStarted.call()

        MovieBusiness.searchMovies(
            query,
            onSuccess = { moviesRes ->
                //                if(moviesRes.size == 0) onEmptySearch.call()
                movies.value = moviesRes
                onLoadFinished.call()
            }, onError = { error ->
                onError.value = error
                onLoadFinished.call()
            })
    }
}