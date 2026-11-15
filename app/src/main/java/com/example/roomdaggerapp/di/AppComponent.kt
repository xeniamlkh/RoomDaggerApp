package com.example.roomdaggerapp.di

import com.example.addword.di.AddWordDependencies
import com.example.database.di.DatabaseComponent
import com.example.network.di.NetworkComponent
import com.example.welcome.di.WelcomeDependencies
import dagger.Component

@AppScope
@Component(
    dependencies = [DatabaseComponent::class, NetworkComponent::class],
    modules = [AppModule::class]
)
interface AppComponent : WelcomeDependencies, AddWordDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            databaseComponent: DatabaseComponent,
            networkComponent: NetworkComponent
        ): AppComponent
    }
}