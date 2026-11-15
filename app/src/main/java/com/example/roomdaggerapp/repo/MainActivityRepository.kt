package com.example.roomdaggerapp.repo

import com.example.database.domain.model.WordItem
import kotlinx.coroutines.flow.Flow

interface MainActivityRepository {
    fun getWords(): Flow<List<WordItem>>
    suspend fun addWord(wordItem: WordItem)
    suspend fun deleteWord(wordItem: WordItem)
    suspend fun searchSentence(word: String): String
}