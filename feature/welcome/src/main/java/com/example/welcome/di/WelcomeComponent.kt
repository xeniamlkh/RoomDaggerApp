package com.example.welcome.di

import com.example.welcome.ui.WelcomeViewModelFactory
import dagger.Component

@Component(dependencies = [WelcomeDependencies::class])
interface WelcomeComponent {

    fun welcomeViewModelFactory(): WelcomeViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: WelcomeDependencies): WelcomeComponent
    }
}