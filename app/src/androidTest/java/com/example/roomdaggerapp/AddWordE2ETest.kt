package com.example.roomdaggerapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.database.domain.DatabaseEntryPoint
import com.example.database.domain.model.WordItem
import com.example.database.domain.repository.IDictionaryRepository
import com.example.network.domain.repository.ITatoebaApi
import com.example.roomdaggerapp.mainscreen.MainActivityScreen
import com.example.roomdaggerapp.mainscreen.MainActivityViewModel
import com.example.roomdaggerapp.repo.MainActivityRepositoryImpl
import com.example.roomdaggerapp.ui.theme.RoomDaggerAppTheme
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class AddWordE2ETest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val tatoebaApi: ITatoebaApi = mock()
    private lateinit var dictionaryRepository: IDictionaryRepository

    @Before
    fun setUp() {
        dictionaryRepository = DatabaseEntryPoint.init(ApplicationProvider.getApplicationContext())

        removeTestWord()

        runBlocking {
            whenever(tatoebaApi.searchSentence(any(), any())).thenReturn(EXAMPLE)
        }

        val viewModel = MainActivityViewModel(
            MainActivityRepositoryImpl(
                dictionaryRepository = dictionaryRepository,
                networkApi = tatoebaApi
            )
        )

        composeTestRule.setContent {
            RoomDaggerAppTheme {
                MainActivityScreen(viewModel)
            }
        }
    }

    @After
    fun tearDown() {
        removeTestWord()
    }

    @Test
    fun userAddsWordWithAutoFilledExampleAndSeesItInSavedList() {
        composeTestRule.onNodeWithText("Add word").assertIsNotEnabled()

        composeTestRule.onNodeWithText("Word").performTextInput(WORD)
        composeTestRule.onNodeWithText("Translation").performClick()

        composeTestRule.waitUntil(TIMEOUT_MS) {
            composeTestRule.onAllNodesWithText(EXAMPLE).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Translation").performTextInput(TRANSLATION)
        composeTestRule.onNodeWithText("Add word").assertIsEnabled().performClick()

        composeTestRule.waitUntil(TIMEOUT_MS) {
            composeTestRule.onAllNodesWithText("Add word")
                .filter(isNotEnabled())
                .fetchSemanticsNodes()
                .isNotEmpty() &&
                    composeTestRule.onAllNodesWithText(WORD).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText(WORD).assertIsDisplayed()
        composeTestRule.onNodeWithText(TRANSLATION).assertIsDisplayed()
        composeTestRule.onNodeWithText(EXAMPLE).assertIsDisplayed()

        verifyBlocking(tatoebaApi) { this.searchSentence(WORD, "eng") }
    }

    private fun removeTestWord() {
        runBlocking {
            dictionaryRepository.deleteWord(WordItem(word = WORD, translation = "", example = ""))
        }
    }

    private companion object {
        const val WORD = "serendipity"
        const val TRANSLATION = "счастливая случайность"
        const val EXAMPLE = "Serendipity led her to a great discovery."
        const val TIMEOUT_MS = 5_000L
    }
}