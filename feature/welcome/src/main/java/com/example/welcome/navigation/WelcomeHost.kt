package com.example.welcome.navigation

import com.example.welcome.di.WelcomeComponent

interface WelcomeHost {
    fun welcomeComponentFactory(): WelcomeComponent.Factory
    fun openAddWord()
}