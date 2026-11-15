package com.example.network.di

import com.example.network.data.TatoebaApiImpl
import com.example.network.domain.repository.ITatoebaApi
import com.example.network.net.TatoebaService
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val TATOEBA_URL = "https://api.tatoeba.org/"

    @Provides
    @Singleton
    fun provideTatoebaApi(): ITatoebaApi {
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(TATOEBA_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        return TatoebaApiImpl(retrofit.create(TatoebaService::class.java))
    }
}