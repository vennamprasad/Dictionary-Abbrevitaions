package com.prasad.abbreviationsfinder.repository

import com.prasad.abbreviationsfinder.model.AcronymData
import com.prasad.abbreviationsfinder.retrofit.api.AbbreviationApiInterface
import javax.inject.Inject

/**
 * This class is used for calling web API, which fetches response for abbreviation/sortForm provided by user.
 */
class AbbreviationRepository @Inject constructor(
    private val abbreviationApiInterface: AbbreviationApiInterface
) {

    suspend fun getMeaningsData(sortForm: String): NetworkState<AcronymData> {
        val response = abbreviationApiInterface.getAcronyms(sortForm)
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

