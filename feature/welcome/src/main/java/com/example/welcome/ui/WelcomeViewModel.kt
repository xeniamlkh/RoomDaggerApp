package com.example.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WordItem
import com.example.domain.repository.MainActivityRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WelcomeViewModel (
    private val repository: MainActivityRepository
) : ViewModel() {

    val words: StateFlow<List<WordItem>> =
        repository.getWords().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun deleteWord(wordItem: WordItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(wordItem)
        }
    }
}

class WelcomeViewModelFactory @Inject constructor(
    private val repository: MainActivityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(
                WelcomeViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel: ${modelClass.name}"
        )
    }
}