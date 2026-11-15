package com.example.database.data

import com.example.database.db.WordEntity
import com.example.domain.model.WordItem

internal fun WordEntity.toModel() = WordItem(
    word = word,
    translation = translation,
    example = example
)

internal fun WordItem.toEntity() = WordEntity(
    word = word,
    translation = translation,
    example = example
)