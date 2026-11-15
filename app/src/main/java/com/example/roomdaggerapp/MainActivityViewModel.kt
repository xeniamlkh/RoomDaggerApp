package com.example.roomdaggerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.database.domain.model.WordItem
import com.example.database.domain.repository.IDictionaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: IDictionaryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState: StateFlow<MainActivityUiState> = _uiState

    val words: StateFlow<List<WordItem>> =
        repository.getWords().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onWordChange(value: String) {
        _uiState.update { it.copy(word = value) }
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

        viewModelScope.launch {
            repository.addWord(wordItem)

            _uiState.update {
                MainActivityUiState()
            }
        }
    }

    fun deleteWord(wordItem: WordItem) {
        viewModelScope.launch {
            repository.deleteWord(wordItem)
        }
    }
}

class MainActivityViewModelFactory(
    private val repository: IDictionaryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}