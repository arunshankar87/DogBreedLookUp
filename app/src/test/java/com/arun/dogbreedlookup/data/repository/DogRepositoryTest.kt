package com.arun.dogbreedlookup.data.repository
import com.arun.dogbreedlookup.data.api.DogApiService
import com.arun.dogbreedlookup.data.api.DogBreedsResponse
import com.arun.dogbreedlookup.data.api.DogImageResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DogRepositoryTest {

    @Mock
    private lateinit var apiService: DogApiService

    private lateinit var repository: DogRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = DogRepository(apiService)
    }

    @Test
    fun `test getRandomDogImage returns image url`() = runBlocking {
        val expectedUrl = "https://example.com/dog.jpg"
        `when`(apiService.getRandomDogImage()).thenReturn(DogImageResponse(expectedUrl, "success"))

        val result = repository.getRandomDogImage()
        assertEquals(expectedUrl, result.message)
    }

    @Test
    fun `test getAllBreeds returns breed list`() = runBlocking {
        val expectedBreeds = mapOf("breed1" to listOf("subbreed1"), "breed2" to listOf())
        `when`(apiService.getAllBreeds()).thenReturn(DogBreedsResponse(expectedBreeds, "success"))

        val result = repository.getAllBreeds()
        assertEquals(expectedBreeds, result.message)
    }
}