package com.arun.dogbreedlookup.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arun.dogbreedlookup.data.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DogQuizState {
    data object Idle: DogQuizState()
    data object Loading : DogQuizState()
    data class Success(
        val imageUrl: String,
        val correctBreed: String,
        val options: List<String>,
        val isCorrectChoicePicked: Boolean? = null
    ) : DogQuizState()
    data class Error(val message: String) : DogQuizState()
}

class DogViewModel(private val repository: DogRepository) : ViewModel() {

    @VisibleForTesting
    internal val _dogQuizState = MutableStateFlow<DogQuizState>(DogQuizState.Idle)
    val dogQuizState: StateFlow<DogQuizState> = _dogQuizState

    fun fetchDogQuiz() {
        viewModelScope.launch {
            _dogQuizState.value = DogQuizState.Loading
            try {
                val imageResponse = repository.getRandomDogImage()
                val breedsResponse = repository.getAllBreeds()

                val allBreeds = breedsResponse.message.keys.toList()
                val correctBreed = imageResponse.message.split("/")[4]
                val options = allBreeds.shuffled().take(3).toMutableList().apply {
                    add(correctBreed)
                    shuffle()
                }

                _dogQuizState.value = DogQuizState.Success(
                    imageUrl = imageResponse.message,
                    correctBreed = correctBreed,
                    options = options
                )
            } catch (e: Exception) {
                _dogQuizState.value = DogQuizState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun checkAnswer(selectedBreed: String) {
        val currentState = _dogQuizState.value
        if (currentState is DogQuizState.Success) {
            _dogQuizState.value = currentState.copy(
                isCorrectChoicePicked = selectedBreed == currentState.correctBreed
            )
        }
    }
}