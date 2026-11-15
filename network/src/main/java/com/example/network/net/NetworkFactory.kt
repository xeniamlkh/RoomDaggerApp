package com.example.network.net

import com.example.network.data.TatoebaApiImpl
import com.example.network.domain.repository.ITatoebaApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal object NetworkFactory {

    private const val TATOEBA_BASE_URL = "https://api.tatoeba.org/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(TATOEBA_BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    private val tatoebaService: TatoebaService by lazy {
        retrofit.create(TatoebaService::class.java)
    }

    fun createTatoebaApi(): ITatoebaApi {
        return TatoebaApiImpl(tatoebaService)
    }
}