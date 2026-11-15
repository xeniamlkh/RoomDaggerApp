package com.example.network.di

import com.example.network.data.TatoebaApiImpl
import com.example.network.domain.repository.ITatoebaApi
import com.example.network.net.TatoebaService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
internal abstract class NetworkModule {

    @Binds
    abstract fun bindTatoebaApi(impl: TatoebaApiImpl): ITatoebaApi

    companion object {
        private const val TATOEBA_URL = "https://api.tatoeba.org/"

        @Provides
        @Singleton
        fun provideJson(): Json = Json { ignoreUnknownKeys = true }

        @Provides
        @Singleton
        fun provideRetrofit(json: Json): Retrofit =
            Retrofit.Builder()
                .baseUrl(TATOEBA_URL)
                .addConverterFactory(
                    json.asConverterFactory("application/json".toMediaType())
                )
                .build()

        @Provides
        @Singleton
        fun provideTatoebaService(retrofit: Retrofit): TatoebaService =
            retrofit.create(TatoebaService::class.java)
    }
}