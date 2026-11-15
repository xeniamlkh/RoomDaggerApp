package com.example.network.di

import com.example.network.domain.repository.ITatoebaApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun tatoebaApi(): ITatoebaApi

    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }
}