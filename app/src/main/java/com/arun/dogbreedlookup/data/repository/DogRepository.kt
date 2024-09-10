package com.arun.dogbreedlookup.data.repository

import com.arun.dogbreedlookup.data.api.DogApiService
import com.arun.dogbreedlookup.data.api.DogImageResponse
import com.arun.dogbreedlookup.data.api.DogBreedsResponse

class DogRepository(private val apiService: DogApiService) {
    suspend fun getRandomDogImage(): DogImageResponse {
        return apiService.getRandomDogImage()
    }

    suspend fun getAllBreeds(): DogBreedsResponse {
        return apiService.getAllBreeds()
    }
}