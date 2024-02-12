package com.prasad.abbreviationsfinder.repository

import com.prasad.abbreviationsfinder.api.network.AbbreviationApiInterface
import com.prasad.abbreviationsfinder.model.MeaningsData
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
class AbbreviationsRepositoryTest {

    private lateinit var abbreviationRepository: AbbreviationRepository

    @Mock
    lateinit var abbreviationApiInterface: AbbreviationApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        abbreviationRepository = AbbreviationRepository(abbreviationApiInterface)
    }

    @Test
    fun `get meanings data test`() {
        runBlocking {
            val acronymData = MeaningsData()
            Mockito.`when`(abbreviationApiInterface.getAcronyms("sf")).thenReturn(
                Response.success(acronymData)
            )

            val response = abbreviationRepository.getMeaningsData("sf")
            assertEquals(NetworkState.Success(acronymData), response)
        }
    }
}