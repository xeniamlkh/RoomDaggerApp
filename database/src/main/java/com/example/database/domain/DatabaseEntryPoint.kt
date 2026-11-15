package com.example.database.domain

import android.content.Context
import com.example.database.data.DictionaryRepositoryImpl
import com.example.database.db.DictionaryDatabase
import com.example.database.domain.repository.IDictionaryRepository

object DatabaseEntryPoint {
    @Volatile private var repository: IDictionaryRepository? = null

    fun init(context: Context = context.applicationContext): IDictionaryRepository =
        repository ?: synchronized(this) {
            repository ?: DictionaryRepositoryImpl(
                DictionaryDatabase.createDatabase(context.applicationContext).dictionaryDao()
            ).also { repository = it }
        }

    fun getDictionaryRepository(): IDictionaryRepository =
        repository ?: error("DatabaseEntryPoint.init() was not called")
}