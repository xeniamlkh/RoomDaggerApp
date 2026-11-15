package com.example.database.db

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
internal abstract class DictionaryDatabase : RoomDatabase() {
    internal abstract fun dictionaryDao(): DictionaryDao
}