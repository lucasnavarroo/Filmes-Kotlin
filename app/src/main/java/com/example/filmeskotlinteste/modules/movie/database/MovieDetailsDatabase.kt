package com.example.filmeskotlinteste.modules.movie.database

import com.example.filmeskotlinteste.modules.movie.model.MovieDetails
import io.realm.Realm

object MovieDetailsDatabase {

    fun save(movies: MovieDetails) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(movies)
            realm.commitTransaction()
        }
    }

    fun get(): MovieDetails {
        return Realm.getDefaultInstance().where(MovieDetails::class.java).findFirstAsync()
    }
}