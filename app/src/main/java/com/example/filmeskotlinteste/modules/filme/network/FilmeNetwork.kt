package com.example.filmeskotlinteste.modules.filme.network

import com.example.filmeskotlinteste.core.network.BaseNetwork
import com.example.filmeskotlinteste.modules.filme.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object FilmeNetwork : BaseNetwork() {

    private val API by lazy { getRetrofitBuilder().build().create(FilmeAPI::class.java) }

    var getFilmes: Disposable? = null

    fun requestFilmesFromAPI(
        page: Int,
        onSuccess: (response: MutableList<Movie>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        getFilmes?.dispose()

        getFilmes = API.getFilmes(page.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filmes ->
                filmes.results?.let { onSuccess(it) }
            }, {
                onError(it.message.toString())
            })
    }
}