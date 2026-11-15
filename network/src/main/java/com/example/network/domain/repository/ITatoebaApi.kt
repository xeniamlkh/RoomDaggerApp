package com.example.network.domain.repository

interface ITatoebaApi {
    suspend fun searchSentence(
        word: String,
        language: String = "eng"
    ): String
}