package com.example.roomdaggerapp

import com.example.database.domain.model.WordItem
import com.example.database.domain.repository.IDictionaryRepository
import com.example.network.domain.repository.ITatoebaApi
import com.example.roomdaggerapp.mainscreen.MainActivityUiState
import com.example.roomdaggerapp.mainscreen.MainActivityViewModel
import com.example.roomdaggerapp.repo.MainActivityRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class WordFlowIntegrationTest {
    private val dictionaryRepository: IDictionaryRepository = mock()
    private val tatoebaApi: ITatoebaApi = mock()
    private val storedWords = MutableStateFlow<List<WordItem>>(emptyList())

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        runBlocking {
            whenever(dictionaryRepository.getWords()).thenReturn(storedWords)
            whenever(dictionaryRepository.addWord(any())).thenAnswer { invocation ->
                storedWords.value += (invocation.arguments[0] as WordItem)
                Unit
            }
            whenever(tatoebaApi
                .searchSentence(any(), any()))
                .thenReturn(EXAMPLE)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetched example and added word travel through viewmodel and repository`() = runBlocking<Unit> {
        val viewModel = MainActivityViewModel(
            MainActivityRepositoryImpl(
                dictionaryRepository = dictionaryRepository,
                networkApi = tatoebaApi
            )
        )

        viewModel.onWordChange("  hello ")
        viewModel.onWordFocusEnded()

        val stateWithExample = withTimeout(TIMEOUT_MS.milliseconds) {
            viewModel.uiState.first { it.example.isNotEmpty() }
        }
        assertEquals(EXAMPLE, stateWithExample.example)
        verify(tatoebaApi).searchSentence("hello", "eng")

        viewModel.onTranslationChange("привет")
        viewModel.addWord()

        val expectedItem = WordItem(word = "hello", translation = "привет", example = EXAMPLE)

        val savedWords = withTimeout(TIMEOUT_MS.milliseconds) {
            viewModel.words.first { it.isNotEmpty() }
        }
        assertEquals(listOf(expectedItem), savedWords)
        verify(dictionaryRepository).addWord(expectedItem)

        withTimeout(TIMEOUT_MS.milliseconds) {
            viewModel.uiState.first { it == MainActivityUiState() }
        }
    }

    private companion object {
        const val EXAMPLE = "Hello, how are you?"
        const val TIMEOUT_MS = 5_000L
    }
}