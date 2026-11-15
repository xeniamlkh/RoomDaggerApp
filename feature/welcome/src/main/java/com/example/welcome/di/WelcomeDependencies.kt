package com.example.welcome.di

import com.example.domain.repository.MainActivityRepository

interface WelcomeDependencies {
    fun mainActivityRepository(): MainActivityRepository
}