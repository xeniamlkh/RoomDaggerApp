package com.example.network.net

import com.example.network.data.internalModel.SentenceSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TatoebaService {
    @GET("v1/sentences")
    suspend fun searchSentences(
        @Query("lang") language: String,
        @Query("q") word: String,
        @Query("word_count") wordCount: String = "5-10",
        @Query("sort") sort: String = "random",
        @Query("limit") limit: Int = 1
    ): SentenceSearchResponse
}