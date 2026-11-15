package com.example.roomdaggerapp.di

import com.example.domain.repository.MainActivityRepository
import com.example.roomdaggerapp.repo.MainActivityRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(subcomponents = [MainActivityComponent::class])
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindMainActivityRepository(impl: MainActivityRepositoryImpl): MainActivityRepository
}