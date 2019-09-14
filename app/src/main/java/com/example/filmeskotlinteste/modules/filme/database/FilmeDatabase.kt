package com.example.filmeskotlinteste.modules.filme.database

import com.example.filmeskotlinteste.modules.filme.model.Movie
import io.realm.Realm

object FilmeDatabase {

    fun save(consultas: MutableList<Movie>) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(consultas)
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