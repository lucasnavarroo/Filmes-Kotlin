package com.example.filmeskotlinteste.core.application

import android.app.Application
import io.realm.Realm

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupRealm()
    }

    private fun setupRealm() {
        Realm.init(this)
    }
}