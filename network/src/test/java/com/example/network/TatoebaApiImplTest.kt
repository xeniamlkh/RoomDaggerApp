package com.example.network

import com.example.network.data.TatoebaApiImpl
import com.example.network.data.internalModel.SentenceSearchResponse
import com.example.network.data.internalModel.TatoebaSentence
import com.example.network.net.TatoebaService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TatoebaApiImplTest {
    private val service: TatoebaService = mock()
    private val api = TatoebaApiImpl(service)

    @Test
    fun `searchSentence trims the word and returns text of the first sentence`() = runTest {
        whenever(service
            .searchSentences(any(), any(), any(), any(), any()))
            .thenReturn(
                SentenceSearchResponse(
                    data = listOf(
                        TatoebaSentence(id = 1, text = "Hello, world!"),
                        TatoebaSentence(id = 2, text = "Hello again.")
                    )
                )
            )

        val result = api.searchSentence(word = "  hello  ", language = "eng")

        assertEquals("Hello, world!", result)
        verify(service)
            .searchSentences(eq("eng"), eq("hello"), any(), any(), any())
    }
}