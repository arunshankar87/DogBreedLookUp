package com.arun.dogbreedlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arun.dogbreedlookup.data.api.DogApiService
import com.arun.dogbreedlookup.data.repository.DogRepository
import com.arun.dogbreedlookup.ui.compose.DogImageScreen
import com.arun.dogbreedlookup.ui.theme.DogBreedLookupTheme
import com.arun.dogbreedlookup.ui.viewmodel.DogViewModel
import com.arun.dogbreedlookup.ui.viewmodel.DogViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels {
        DogViewModelFactory(DogRepository(DogApiService.create()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedLookupTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DogImageScreen(viewModel)
                }
            }
        }
    }
}