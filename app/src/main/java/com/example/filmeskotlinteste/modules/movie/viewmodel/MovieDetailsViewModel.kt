package com.example.filmeskotlinteste.modules.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmeskotlinteste.core.livedata.SingleLiveEvent
import com.example.filmeskotlinteste.modules.movie.business.MovieBusiness
import com.example.filmeskotlinteste.modules.movie.model.MovieDetails

class MovieDetailsViewModel : ViewModel() {

    var movie: MutableLiveData<MovieDetails> = MutableLiveData()

    val onLoadFinished = SingleLiveEvent<Void>()
    val onError = SingleLiveEvent<String>()

    fun requestMovie(movieId: Int) {
        MovieBusiness.getMovie(movieId, onSuccess = { movieDetails ->
            movie.value = movieDetails
            onLoadFinished.call()
        }, onError = { errorMessage ->
            onError.value = errorMessage
        })
    }
}
