package com.example.weatherapp.data.remote.repository

import com.example.weatherapp.data.remote.local.WeatherDao
import com.example.weatherapp.data.remote.model.WeatherEntity
import com.example.weatherapp.data.remote.RetrofitClient
import com.example.weatherapp.BuildConfig

class WeatherRepository(private val weatherDao: WeatherDao,
                        private val searchHistoryRepository: SearchHistoryRepository
) {

    suspend fun getWeather(city: String): WeatherEntity {
        val thirtyMinutes = 30 * 60 * 1000
        val thirtyMinutesAgo = System.currentTimeMillis() - thirtyMinutes
        deleteOlderThan(thirtyMinutesAgo)
        val cached = weatherDao.getWeatherByCity(city)


        return if (cached != null && System.currentTimeMillis() - cached.timestamp < thirtyMinutes) {
            println("WeatherRepository: Käytetään välimuistissa olevaa dataa Roomista: $city")
            return  cached
        } else {

            println("WeatherRepository: Haetaan data API:sta: $city")

            val apiResult = RetrofitClient.apiService.getWeather(
                city,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
                units = "metric"
            )

            val entity = WeatherEntity(
                city = apiResult.name,
                description = apiResult.weather.firstOrNull()?.description ?: "No data",
                temperature = apiResult.main.temp,
                feelsLike = apiResult.main.feels_like,
                humidity = apiResult.main.humidity,
                windSpeed = apiResult.wind.speed,
                timestamp = System.currentTimeMillis()
            )
            weatherDao.insert(entity)

            searchHistoryRepository.addHistory(city)

            entity
        }
    }

    suspend fun deleteOlderThan(time: Long) {
        weatherDao.deleteByTimestamp(time)
    }
}
