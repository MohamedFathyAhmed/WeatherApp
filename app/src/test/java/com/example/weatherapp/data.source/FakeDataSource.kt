
package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import com.example.weatherapp.model.SimpleService

import com.example.weatherapp.model.Welcome

class FakeDataSource(var welcome: Welcome) : SimpleService {

    override suspend fun getCurrentWeather(
        lat: String?,
        lon: String?,
        units: String?,
        lang: String?,
        exclude: String,
        appId: String
    ): Welcome {
    return  welcome
    }

}