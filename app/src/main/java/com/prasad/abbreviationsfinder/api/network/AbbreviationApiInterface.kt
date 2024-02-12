package com.prasad.abbreviationsfinder.api.network

import com.prasad.abbreviationsfinder.model.MeaningsData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    @GET("dictionary.py")
    suspend fun getAcronyms(@Query("sf") query: String) : Response<MeaningsData>
    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"
        private var retrofitService: AbbreviationApiInterface? = null
        fun getInstance(): AbbreviationApiInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
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