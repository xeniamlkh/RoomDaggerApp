package com.example.database.di

import android.content.Context
import com.example.database.domain.repository.IDictionaryRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun dictionaryRepository(): IDictionaryRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DatabaseComponent
    }
}