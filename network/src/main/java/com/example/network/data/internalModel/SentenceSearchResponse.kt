package com.example.network.data.internalModel

import kotlinx.serialization.Serializable

@Serializable
internal data class SentenceSearchResponse(
    val data: List<TatoebaSentence> = emptyList()
)