package com.example.weatherapp.data.remote.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

/*{ "coord":
    { "lon": 24.9355, "lat": 60.1695 },
    "weather":
    [ { "id": 803, "main": "Clouds", "description": "broken clouds", "icon": "04d" } ],
    "base":
    "stations",
    "main":
    { "temp": -10.29, "feels_like": -16.01,
        "temp_min": -11.24, "temp_max": -8.4,
        "pressure": 1015, "humidity": 86, "sea_level": 1015,
        "grnd_level": 1013 }, "visibility": 10000,
    "wind":
    { "speed": 3.13, "deg": 270, "gust": 4.02 },
    "clouds":
    { "all": 75 }, "dt": 1771150569, "sys":
    { "type": 2, "id": 2011913,
        "country":
        "FI", "sunrise": 1771135194, "sunset": 1771168149 },
    "timezone": 7200, "id": 658225,
    "name": "Helsinki", "cod": 200*/