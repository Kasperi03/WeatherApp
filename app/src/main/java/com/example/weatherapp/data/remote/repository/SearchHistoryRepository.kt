package com.example.weatherapp.data.remote.repository

import com.example.weatherapp.data.remote.local.SearchHistoryDao
import com.example.weatherapp.data.remote.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepository(private val dao: SearchHistoryDao) {

    suspend fun addHistory(city: String) {
        dao.deleteByCity(city)
        dao.insert(SearchHistoryEntity(city = city))
    }

    fun getAllHistory(): Flow<List<SearchHistoryEntity>> {
        return dao.getAllHistory()
    }
}