package com.example.weatherapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.viewmodel.WeatherViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val weatherList by viewModel.weather.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("$error")
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        var city by remember { mutableStateOf("") }

        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Write city") },
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.setCity(city)
            }) {
                Text("Search")
            }
        }

        if (searchHistory.isNotEmpty()) {
            Text("Viimeisimmät haut:", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
                items(searchHistory) { history ->
                    Text(
                        text = history.city,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                city = history.city
                                viewModel.setCity(history.city)
                            },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Blue
                    )
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(weatherList) { weather ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = weather.city, style = MaterialTheme.typography.titleMedium)
                    Text(text = weather.description)
                    Text(text = "Temperature: ${weather.temperature}°C")
                    Text(text = "Feels like: ${weather.feelsLike}°C")
                    Text(text = "Humidity: ${weather.humidity}%")
                    Text(text = "Wind: ${weather.windSpeed} m/s")
                }
            }
        }
    }
}