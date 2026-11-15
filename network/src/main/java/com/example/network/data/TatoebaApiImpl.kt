package com.example.network.data

import com.example.network.domain.repository.ITatoebaApi
import com.example.network.net.TatoebaService

internal class TatoebaApiImpl(private val service: TatoebaService): ITatoebaApi {
    override suspend fun searchSentence(
        word: String,
        language: String
    ): String {
        if (word.isBlank()) return ""

        val response = service.searchSentences(
            language = language,
            word = word.trim()
        )

        return response.data.firstOrNull()?.text.orEmpty()
    }
}