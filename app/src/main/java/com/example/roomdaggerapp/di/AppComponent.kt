package com.example.roomdaggerapp.di

import android.content.Context
import com.example.core.ApplicationContext
import com.example.database.di.DatabaseModule
import com.example.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, NetworkModule::class])
interface AppComponent {
    fun mainActivityComponentFactory(): MainActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext context: Context): AppComponent
    }
}