package com.example.roomdaggerapp.di

import android.content.Context
import com.example.addword.di.AddWordDependencies
import com.example.core.ActivityContext
import com.example.welcome.di.WelcomeComponent
import com.example.welcome.di.WelcomeModule
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainActivityModule::class, WelcomeModule::class])
interface MainActivityComponent : AddWordDependencies {

    fun welcomeComponentFactory(): WelcomeComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance @ActivityContext context: Context): MainActivityComponent
    }
}