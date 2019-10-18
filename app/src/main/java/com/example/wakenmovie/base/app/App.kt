package com.example.wakenmovie.base.app

import android.app.Application
import com.example.wakenmovie.base.modules.*
import org.koin.android.ext.android.startKoin

@Suppress("unused")
class App : Application() {

    private val modules = listOf(
        appModule,
        featureModule,
        dispatcherModule,
        retrofitClientModule,
        repositoryModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin(this, modules)
    }
}