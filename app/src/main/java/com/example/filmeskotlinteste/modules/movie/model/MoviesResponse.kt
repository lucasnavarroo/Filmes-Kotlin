package com.example.filmeskotlinteste.modules.movie.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class MoviesResponse : RealmObject() {

    var page: Int? = null

    @SerializedName("total_results")
    var totalResults: Int? = null

    @SerializedName("total_pages")
    var totalPages: Int? = null

    var results: RealmList<Movie>? = null
}