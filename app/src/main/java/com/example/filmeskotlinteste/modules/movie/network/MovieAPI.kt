package com.example.filmeskotlinteste.modules.movie.network

import com.example.filmeskotlinteste.modules.movie.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

const val api_key = "3b6b4731c886c4cba888c9df37b88ea4"

interface FilmeAPI {

    @GET("movie/popular?api_key=$api_key&language=en-US&append_to_response=revenue,overview,genres")
    fun getFilmes(
        @Query("page") page: String
    ): Observable<MovieResponse>
}