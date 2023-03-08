package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow

interface ILocalDataSourceInterface {
    suspend fun getCheckCurrentsWeatherDataBase(): Welcome
    fun getCurrentsWeatherDataBase(): Flow<Welcome>
    suspend fun insertCurrentWeatherDataBase(welcome: Welcome): Long
    suspend fun updateCurrentWeatherDataBase(welcome: Welcome)
    fun getFavtsWeatherDataBase(): Flow<List<Welcome>>
    suspend fun insertFavWeatherDataBase(welcome: Welcome): Long
    suspend fun deleteFavWeatherDataBase(welcome: Welcome)
    suspend fun updateFavWeatherDataBase(welcome: Welcome)
    fun getAlertsDataBase(): Flow<List<MyAlert>>
    suspend fun insertAlertDataBase(myAlert: MyAlert)
    suspend fun deleteAlertDataBase(myAlert: MyAlert)
}