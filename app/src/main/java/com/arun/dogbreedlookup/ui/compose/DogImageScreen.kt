package com.arun.dogbreedlookup.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.arun.dogbreedlookup.ui.theme.DogBreedLookupTheme
import com.arun.dogbreedlookup.ui.viewmodel.DogQuizState
import com.arun.dogbreedlookup.ui.viewmodel.DogViewModel
import java.util.Locale


@Composable
fun DogImageScreen(viewModel: DogViewModel = viewModel()) {
    val dogQuizState by viewModel.dogQuizState.collectAsState()
    var selectedOption by remember { mutableStateOf<String?>(null) }

    DogImageScreenContent(
        state = dogQuizState,
        fetchDogQuiz = { viewModel.fetchDogQuiz() },
        checkAnswer = { selectedOption = it; viewModel.checkAnswer(it) }
    )
}

@Composable
fun DogImageScreenContent(
    state: DogQuizState,
    fetchDogQuiz: () -> Unit,
    checkAnswer: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Text(text = "\uD83D\uDC3E Woof! Woof!", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        when (state) {
            is DogQuizState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is DogQuizState.Success -> {
                Image(
                    painter = rememberAsyncImagePainter(state.imageUrl),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.Start // Aligns the start of each option
                ) {
                    state.options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    selectedOption = option
                                    checkAnswer(option)
                                }
                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = {
                                    selectedOption = option
                                    checkAnswer(option)
                                }
                            )
                            Text(
                                text = option.replaceFirstChar {
                                    it.titlecase(Locale.getDefault())
                                },
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(
                    visible = state.isCorrectChoicePicked == true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        Text(text = "\uD83D\uDC3E Pawsome Job!", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(modifier = Modifier.fillMaxWidth().align(alignment = Alignment.CenterHorizontally), onClick = { fetchDogQuiz() }) {
                            Text(text = "Hooray! Next Question")
                        }
                    }
                }
                AnimatedVisibility(
                    visible = state.isCorrectChoicePicked == false,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        Text(text = "Aww! No doubt all breeds are cute, but the picture shown is not it. Let's try again!", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            is DogQuizState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            DogQuizState.Idle -> {
                Button(modifier = Modifier.fillMaxWidth().align(alignment = Alignment.CenterHorizontally), onClick = { fetchDogQuiz() }) {
                    Text(text = "Let's Start!")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DogBreedLookupTheme {
        DogImageScreenContent(
            state = DogQuizState.Success(
                imageUrl = "https://images.dog.ceo/breeds/hound-afghan/n02088094_3057.jpg",
                correctBreed = "Pug1",
                options = listOf("Pug12323", "Pug2", "Pug3", "Pug4"),
                isCorrectChoicePicked = true
            ),
            fetchDogQuiz = {},
            checkAnswer = {})
    }
}