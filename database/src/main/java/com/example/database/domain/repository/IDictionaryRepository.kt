package com.example.database.domain.repository

import com.example.database.domain.model.WordItem
import kotlinx.coroutines.flow.Flow

interface IDictionaryRepository {

    suspend fun addWord(wordItem: WordItem)

    suspend fun deleteWord(wordItem: WordItem)

    fun getWords(): Flow<List<WordItem>>
}