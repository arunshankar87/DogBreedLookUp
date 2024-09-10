package com.arun.dogbreedlookup.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class DogImageResponse(val message: String, val status: String)
data class DogBreedsResponse(val message: Map<String, List<String>>, val status: String)

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImageResponse

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): DogBreedsResponse

    companion object {
        private const val BASE_URL = "https://dog.ceo/api/"

        fun create(): DogApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DogApiService::class.java)
        }
    }
}