package com.arun.dogbreedlookup.ui.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arun.dogbreedlookup.data.api.DogBreedsResponse
import com.arun.dogbreedlookup.data.api.DogImageResponse
import com.arun.dogbreedlookup.data.repository.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DogViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DogRepository

    private lateinit var viewModel: DogViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DogViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `test fetchDogQuiz sets success state`() = runTest {
        val imageUrl = "https://example.com/path/dog.jpg"
        val breeds = mapOf("breed1" to listOf("subbreed1"), "breed2" to listOf())
        `when`(repository.getRandomDogImage()).thenReturn(DogImageResponse(imageUrl, "success"))
        `when`(repository.getAllBreeds()).thenReturn(DogBreedsResponse(breeds, "success"))

        viewModel.fetchDogQuiz()
        advanceUntilIdle()
        val state = viewModel.dogQuizState.value
        assertTrue(state is DogQuizState.Success)
    }

    @Test
    fun `test fetchDogQuiz sets error state`() = runTest {
        `when`(repository.getRandomDogImage()).thenThrow(RuntimeException("Error"))

        viewModel.fetchDogQuiz()
        advanceUntilIdle()
        val state = viewModel.dogQuizState.value
        assertTrue(state is DogQuizState.Error)
    }

    @Test
    fun `test checkAnswer sets isCorrectChoicePicked to true`() = runTest {
        val successState = DogQuizState.Success(
            imageUrl = "https://example.com/path/dog.jpg",
            correctBreed = "breed1",
            options = listOf("breed1", "breed2", "breed3", "breed4")
        )
        viewModel._dogQuizState.value = successState

        viewModel.checkAnswer("breed1")
        val state = viewModel.dogQuizState.value
        assertTrue(state is DogQuizState.Success && state.isCorrectChoicePicked == true)
    }

    @Test
    fun `test checkAnswer sets isCorrectChoicePicked to false`() = runTest {
        val successState = DogQuizState.Success(
            imageUrl = "https://example.com/path/dog.jpg",
            correctBreed = "breed1",
            options = listOf("breed1", "breed2", "breed3", "breed4")
        )
        viewModel._dogQuizState.value = successState

        viewModel.checkAnswer("breed2")
        val state = viewModel.dogQuizState.value
        assertTrue(state is DogQuizState.Success && state.isCorrectChoicePicked == false)
    }
}