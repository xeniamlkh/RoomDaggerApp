package com.example.database.data

import com.example.database.db.DictionaryDao
import com.example.database.domain.repository.IDictionaryRepository
import com.example.domain.model.WordItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DictionaryRepositoryImpl(
    private val dictionaryDao: DictionaryDao
) : IDictionaryRepository {

    override suspend fun addWord(wordItem: WordItem) {
        dictionaryDao.insertWord(wordEntity = wordItem.toEntity())
    }

    override suspend fun deleteWord(wordItem: WordItem) {
        dictionaryDao.deleteWord(wordItem.word)
    }

    override fun getWords(): Flow<List<WordItem>> {
        return dictionaryDao.loadAll().map { words ->
            words.map { word -> word.toModel() }
        }
    }
}