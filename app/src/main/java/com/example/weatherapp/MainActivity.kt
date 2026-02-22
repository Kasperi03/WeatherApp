package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.remote.local.AppDatabase
import com.example.weatherapp.data.remote.repository.SearchHistoryRepository
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.view.WeatherScreen
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val searchHistoryRepository by lazy { SearchHistoryRepository(database.searchHistoryDao()) }
    val weatherRepository by lazy { WeatherRepository(database.weatherDao(), searchHistoryRepository) }
    private val viewModel: WeatherViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(weatherRepository, searchHistoryRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherScreen(viewModel = viewModel)
        }
    }
}