package com.example.weatherapp.data.remote.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.remote.model.SearchHistoryEntity
import com.example.weatherapp.data.remote.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity): Long

    @Insert
    suspend fun insertAll(vararg weatherList: WeatherEntity)

    @Query("SELECT * FROM weather")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather WHERE LOWER(city) = LOWER(:city)")
    suspend fun getWeatherByCity(city: String): WeatherEntity?

    @Query("DELETE FROM weather WHERE timestamp = :time")
    suspend fun deleteByTimestamp(time: Long)
}