package com.prasad.abbreviationsfinder.repository

import com.prasad.abbreviationsfinder.model.Dictionary
import com.prasad.abbreviationsfinder.api.network.DictionaryApiInterface
import com.prasad.abbreviationsfinder.utils.NetworkState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class DictionaryRepositoryTest {

    private lateinit var dictionaryRepository: DictionaryRepository

    @Mock
    lateinit var dictionaryApiInterface: DictionaryApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dictionaryRepository = DictionaryRepository(dictionaryApiInterface)
    }

    @Test
    fun `get meanings data test`() {
        runBlocking {
            val acronymData = listOf<Dictionary>()
            Mockito.`when`(dictionaryApiInterface.getDictionary("sf")).thenReturn(
                Response.success(acronymData)
            )

            val response = dictionaryRepository.getDictionaryData("sf")
            assertEquals(NetworkState.Success(acronymData), response)
        }
    }
}