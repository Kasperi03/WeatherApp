package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.model.SearchHistoryEntity
import com.example.weatherapp.data.remote.repository.SearchHistoryRepository
import com.example.weatherapp.data.remote.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    data class WeatherList(
        val city: String,
        val description: String,
        val temperature: Double,
        val feelsLike: Double,
        val humidity: Int,
        val windSpeed: Double
    )
    private val _weather = MutableStateFlow<List<WeatherList>>(emptyList())
    val weather: StateFlow<List<WeatherList>> = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val searchHistory: StateFlow<List<SearchHistoryEntity>> =
        searchHistoryRepository.getAllHistory()
            .stateIn(viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList())

    fun setCity(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                searchHistoryRepository.addHistory(city)
                val entity = weatherRepository.getWeather(city)

                _weather.value = listOf(
                    WeatherList(
                        city = entity.city,
                        description = entity.description,
                        temperature = entity.temperature,
                        feelsLike = entity.feelsLike,
                        humidity = entity.humidity,
                        windSpeed = entity.windSpeed
                    )
                )
            } catch (e: Exception) {
                _error.value = "Virhe ladattaessa tietoja: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}