package com.example.addword.di

import androidx.lifecycle.ViewModelProvider
import com.example.addword.ui.AddWordViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface AddWordModule {
    @Binds
    fun bindViewModelFactory(impl: AddWordViewModelFactory): ViewModelProvider.Factory
}