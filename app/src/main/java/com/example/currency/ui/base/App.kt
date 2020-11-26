package com.example.currency.ui.base

import android.app.Application
import android.content.Context
import com.example.currency.data.network.IApiCaller
import com.example.currency.util.preferences.MySharedPreferences
import com.example.currency.util.sqlite.CountriesSQLiteManager
import com.example.currency.util.sqlite.UsersSQLiteManager

class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App = App()

        fun applicationContext(): Context? {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        applicationContext()
        IApiCaller.initWith(this)
        MySharedPreferences.initWith(this)
        UsersSQLiteManager.initWith(this)
        CountriesSQLiteManager.initWith(this)

    }

}