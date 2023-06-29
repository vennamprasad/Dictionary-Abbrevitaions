package com.prasad.abbreviationsfinder.retrofit.api

import com.prasad.abbreviationsfinder.model.AcronymData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * This is ApiInterface, which provides Retrofit Client to call web API.
 * It also declares GET API to fetch full form response for the sort form provided by user.
 */
interface AbbreviationApiInterface {


    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(@Query("sf") query: String) : Response<AcronymData>

    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/"
        private var retrofitService: AbbreviationApiInterface? = null
        fun getInstance(): AbbreviationApiInterface {
            val client = OkHttpClient.Builder()
                .build()
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(AbbreviationApiInterface::class.java)
            }
            return retrofitService!!
        }
    }
}