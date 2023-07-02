package com.prasad.abbreviationsfinder.repository

import com.prasad.abbreviationsfinder.model.Dictionary
import com.prasad.abbreviationsfinder.api.network.DictionaryApiInterface
import com.prasad.abbreviationsfinder.utils.NetworkState
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val dictionaryRepository: DictionaryApiInterface
) {
    suspend fun getDictionaryData(word: String): NetworkState<List<Dictionary>> {
        val response = dictionaryRepository.getDictionary(word)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}