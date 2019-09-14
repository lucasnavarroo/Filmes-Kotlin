package com.example.filmeskotlinteste.modules.filme.network

import com.example.filmeskotlinteste.modules.filme.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val api_key = "3b6b4731c886c4cba888c9df37b88ea4"

interface FilmeAPI {

    @GET("movie/top_rated?api_key=$api_key&language=en-US")
    fun getFilmes(
        @Query("page") page: String
    ): Observable<MovieResponse>
}