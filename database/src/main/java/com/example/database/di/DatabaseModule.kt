package com.example.database.di

import android.content.Context
import androidx.room3.Room
import com.example.database.data.DictionaryRepositoryImpl
import com.example.database.db.DictionaryDao
import com.example.database.db.DictionaryDatabase
import com.example.database.domain.repository.IDictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal abstract class DatabaseModule {

    @Binds
    abstract fun bindRepository(impl: DictionaryRepositoryImpl): IDictionaryRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(context: Context): DictionaryDatabase =
            Room.databaseBuilder<DictionaryDatabase>(
                context.applicationContext,
                "words.db"
            ).build()

        @Provides
        fun provideDao(database: DictionaryDatabase): DictionaryDao =
            database.dictionaryDao()
    }
}