package com.example.network.data.internalModel

import kotlinx.serialization.Serializable

@Serializable
internal data class TatoebaSentence(
    val id: Int,
    val text: String
)