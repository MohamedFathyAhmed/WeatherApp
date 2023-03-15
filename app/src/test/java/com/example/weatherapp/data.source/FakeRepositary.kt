package com.example.weatherapp.data.source

import com.example.weatherapp.model.MyAlert
import com.example.weatherapp.model.RepositaryInterface
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.flow.Flow

class FakeRepositary(var data: MutableList<Welcome> = mutableListOf()):RepositaryInterface {
    override suspend fun getCheckCurrentsWeatherDataBase(): Welcome {
        TODO("Not yet implemented")
    }

    override fun getCurrentsWeatherDataBase(): Flow<Welcome> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentWeatherDataBase(welcome: Welcome): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrentWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
    }

    override fun getFavtsWeatherDataBase(): Flow<List<Welcome>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavWeatherDataBase(welcome: Welcome): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeatherApi(lat: String?, lon: String?): Flow<Welcome> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeatherApiForWorker(lat: String?, lon: String?): Welcome {
        TODO("Not yet implemented")
    }

    override fun getAlertsDataBase(): Flow<List<MyAlert>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlertDataBase(myAlert: MyAlert) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlertDataBase(myAlert: MyAlert) {
        TODO("Not yet implemented")
    }
}