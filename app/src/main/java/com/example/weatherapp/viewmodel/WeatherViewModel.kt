package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.Double

class WeatherViewModel : ViewModel() {

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
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setCity(city: String){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val cityWeather = RetrofitClient.apiService.getWeather(
                    city,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
                    units = "metric"
                )
                _weather.value = listOf(
                    WeatherList(
                        city = cityWeather.name,
                        description = cityWeather.weather.firstOrNull()?.description ?: "No data",
                        temperature = cityWeather.main.temp,
                        feelsLike = cityWeather.main.feels_like,
                        humidity = cityWeather.main.humidity,
                        windSpeed = cityWeather.wind.speed
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