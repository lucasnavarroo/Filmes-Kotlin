package com.example.filmeskotlinteste.modules.movie.database

import com.example.filmeskotlinteste.modules.movie.model.Movie
import io.realm.Realm

object MovieDatabase {

    fun save(movies: MutableList<Movie>) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(movies)
            realm.commitTransaction()
        }
    }

    fun clear() {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.delete(Movie::class.java)
            realm.commitTransaction()
        }
    }

    fun get(): MutableList<Movie> {
        return Realm.getDefaultInstance().where(Movie::class.java).findAll()
    }
}