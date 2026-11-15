package com.example.addword.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WordItem
import com.example.domain.repository.MainActivityRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWordViewModel (
    private val repository: MainActivityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddWordUiState())

    val uiState: StateFlow<AddWordUiState> = _uiState

    fun onWordChange(value: String) {
        _uiState.update { it.copy(word = value) }
    }

    fun onWordFocusEnded() {
        val currentWord = _uiState.value.word.trim()
        if (currentWord.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sentence = repository.searchSentence(currentWord)
                _uiState.update {
                    it.copy(example = sentence)
                }
            } catch (e: Exception) { }
        }
    }

    fun onTranslationChange(value: String) {
        _uiState.update { it.copy(translation = value) }
    }

    fun onExampleChange(value: String) {
        _uiState.update { it.copy(example = value) }
    }

    fun addWord() {
        val state = _uiState.value

        if (state.word.isBlank() ||
            state.translation.isBlank() ||
            state.example.isBlank()
        ) return

        val wordItem = WordItem(
            word = state.word.trim(),
            translation = state.translation.trim(),
            example = state.example.trim()
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(wordItem)

            _uiState.update {
                AddWordUiState()
            }
        }
    }
}

class AddWordViewModelFactory @Inject constructor(
    private val repository: MainActivityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(
                AddWordViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return AddWordViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel: ${modelClass.name}"
        )
    }
}