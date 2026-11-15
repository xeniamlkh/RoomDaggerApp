package com.example.roomdaggerapp.di

import com.example.domain.repository.MainActivityRepository
import com.example.roomdaggerapp.repo.MainActivityRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    @AppScope
    abstract fun bindMainActivityRepository(impl: MainActivityRepositoryImpl): MainActivityRepository
}