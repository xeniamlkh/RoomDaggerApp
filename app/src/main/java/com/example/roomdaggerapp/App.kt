package com.example.roomdaggerapp

import android.app.Application
import com.example.roomdaggerapp.di.AppComponent
import com.example.database.di.DaggerDatabaseComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.roomdaggerapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        val databaseComponent = DaggerDatabaseComponent.factory().create(applicationContext)
        val networkComponent = DaggerNetworkComponent.factory().create()
        appComponent = DaggerAppComponent.factory().create(databaseComponent, networkComponent)
    }
}