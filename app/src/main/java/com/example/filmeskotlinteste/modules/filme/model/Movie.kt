package com.example.filmeskotlinteste.modules.filme.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie : RealmObject() {

    @PrimaryKey
    var id: Int? = null

    var popularity: Float? = null

    @SerializedName("vote_count")
    var vouteCount: Int? = null

    var video: Boolean? = false

    @SerializedName("genre_ids")
    var genreIds: RealmList<Int>? = null

    @SerializedName("poster_path")
    var posterPath: String? = ""

    var adult: Boolean? = false

    @SerializedName("backdrop_path")
    var backdropPath: String? = ""

    @SerializedName("original_language")
    var originalLanguage: String? = ""

    @SerializedName("original_title")
    var originalTitle: String? = ""

    var title: String? = ""

    @SerializedName("vote_average")
    var voteAverage: Float? = null

    var overview: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null
}
