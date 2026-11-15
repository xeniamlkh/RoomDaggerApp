package com.example.welcome.di

import com.example.core.FeatureScope
import com.example.welcome.ui.WelcomeViewModelFactory
import dagger.Subcomponent

@FeatureScope
@Subcomponent
interface WelcomeComponent {

    fun welcomeViewModelFactory(): WelcomeViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(): WelcomeComponent
    }
}