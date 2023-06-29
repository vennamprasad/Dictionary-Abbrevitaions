package com.prasad.abbreviationsfinder.repository

import com.prasad.abbreviationsfinder.model.AcronymData
import com.prasad.abbreviationsfinder.retrofit.api.AbbreviationApiInterface
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
class MainRepositoryTest {

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
            val acronymData = AcronymData()
            Mockito.`when`(abbreviationApiInterface.getAcronyms("sf")).thenReturn(
                Response.success(acronymData)
            )

            val response = abbreviationRepository.getMeaningsData("sf")
            assertEquals(NetworkState.Success(acronymData), response)
        }
    }
}