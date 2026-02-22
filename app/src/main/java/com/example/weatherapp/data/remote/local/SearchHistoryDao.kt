package com.example.weatherapp.data.remote.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.remote.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE city = :city")
    suspend fun deleteByCity(city: String)

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 5")
    fun getAllHistory(): Flow<List<SearchHistoryEntity>>
}