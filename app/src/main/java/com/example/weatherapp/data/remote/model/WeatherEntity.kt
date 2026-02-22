package com.example.weatherapp.data.remote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "temperature")
    val temperature: Double,

    @ColumnInfo(name = "feelslike")
    val feelsLike: Double,

    @ColumnInfo(name = "humidity")
    val humidity: Int,

    @ColumnInfo(name = "windspeed")
    val windSpeed: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)