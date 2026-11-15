package com.example.roomdaggerapp

import android.app.Application
import com.example.roomdaggerapp.di.AppComponent
import com.example.roomdaggerapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}