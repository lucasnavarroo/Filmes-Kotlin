package com.example.filmeskotlinteste.modules.filme.network

import com.example.filmeskotlinteste.modules.filme.model.Movie
import com.example.filmeskotlinteste.modules.filme.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface FilmeAPI {

    @GET("movie/top_rated?api_key=3b6b4731c886c4cba888c9df37b88ea4&language=en-US&page=1")
    fun getFilmes(): Observable<MovieResponse>
}