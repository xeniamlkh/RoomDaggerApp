package com.example.database.di

import android.content.Context
import androidx.room3.Room
import com.example.core.ApplicationContext
import com.example.database.data.DictionaryRepositoryImpl
import com.example.database.db.DictionaryDatabase
import com.example.database.domain.repository.IDictionaryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        @ApplicationContext context: Context
    ): IDictionaryRepository {
        val database = Room.databaseBuilder<DictionaryDatabase>(context, "words.db").build()
        return DictionaryRepositoryImpl(database.dictionaryDao())
    }
}