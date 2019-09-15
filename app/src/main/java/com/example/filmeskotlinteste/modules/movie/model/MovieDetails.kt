package com.example.filmeskotlinteste.modules.movie.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieDetails : RealmObject() {

    @PrimaryKey
    var id: Int? = null

    var title: String? = null

    @SerializedName("vote_average")
    var voteAverage: Float? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    var tagline: String? = null

    var overview: String? = null

    @SerializedName("revenue")
    var revenue: Int? = null
}
