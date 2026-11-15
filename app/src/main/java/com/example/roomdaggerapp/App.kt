package com.example.roomdaggerapp

import android.app.Application
import com.example.database.domain.DatabaseEntryPoint
import com.example.network.domain.NetworkEntryPoint

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseEntryPoint.init(this)
        NetworkEntryPoint.init()
    }
}