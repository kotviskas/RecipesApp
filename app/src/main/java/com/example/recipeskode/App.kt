package com.example.recipeskode

import android.app.Application
import com.example.recipeskode.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelModule, retrofitModule, apiModule, repositoryModule, appModule, useCaseModule))
        }
    }
}