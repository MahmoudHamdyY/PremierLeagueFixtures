package com.fakss.premierleaguefixtures

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

        lateinit var instance: App

        fun appContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}