package com.example.addword.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [AddWordDependencies::class],
    modules = [AddWordModule::class]
)
interface AddWordComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: AddWordDependencies): AddWordComponent
    }
}