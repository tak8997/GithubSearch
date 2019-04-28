package com.github.byungtak.githubsearch

import android.app.Application
import com.github.byungtak.githubsearch.di.appModule
import org.koin.android.ext.android.startKoin

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin(this, listOf(appModule))
    }
}