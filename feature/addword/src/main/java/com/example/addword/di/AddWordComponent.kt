package com.example.addword.di

import com.example.addword.ui.AddWordViewModelFactory
import dagger.Component

@Component(dependencies = [AddWordDependencies::class])
interface AddWordComponent {

    fun addWordViewModelFactory(): AddWordViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: AddWordDependencies): AddWordComponent
    }
}