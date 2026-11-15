package com.example.addword.di

import com.example.domain.repository.MainActivityRepository

interface AddWordDependencies {
    fun mainActivityRepository(): MainActivityRepository
}