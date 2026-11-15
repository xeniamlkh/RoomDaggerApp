package com.example.roomdaggerapp.repo

import com.example.database.domain.model.WordItem
import com.example.database.domain.repository.IDictionaryRepository
import com.example.network.domain.repository.ITatoebaApi
import kotlinx.coroutines.flow.Flow

class MainActivityRepositoryImpl(
    private val dictionaryRepository: IDictionaryRepository,
    private val networkApi: ITatoebaApi
) : MainActivityRepository {
    override fun getWords(): Flow<List<WordItem>> =
        dictionaryRepository.getWords()

    override suspend fun addWord(wordItem: WordItem) {
        dictionaryRepository.addWord(wordItem)
    }

    override suspend fun deleteWord(wordItem: WordItem) {
        dictionaryRepository.deleteWord(wordItem)
    }

    override suspend fun searchSentence(word: String): String =
        networkApi.searchSentence(word)
}