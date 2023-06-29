package com.prasad.abbreviationsfinder.retrofit.api

import com.prasad.abbreviationsfinder.model.AcronymData
import com.prasad.abbreviationsfinder.model.Dictionary
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This is ApiInterface, which provides Retrofit Client to call web API.
 * It also declares GET API to fetch full meaning response for the sort provided by user.
 */
interface DictionaryApiInterface {


    @GET("v2/entries/en/{word}")
    suspend fun getDictionary( @Path("word") word: String?) : Response<List<Dictionary>>

    companion object {
        private const val BASE_URL = "https://api.dictionaryapi.dev/api/"
        private var retrofitService: DictionaryApiInterface? = null
        fun getInstance(): DictionaryApiInterface {
            val client = OkHttpClient.Builder().build()
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(DictionaryApiInterface::class.java)
            }
            return retrofitService!!
        }
    }
}