package com.example.database.db

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
internal abstract class DictionaryDatabase : RoomDatabase() {
    internal abstract fun dictionaryDao(): DictionaryDao

    companion object DatabaseFactory {
        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun createDatabase(context: Context = context.applicationContext): DictionaryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder<DictionaryDatabase>(
                    context.applicationContext,
                    "words.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}