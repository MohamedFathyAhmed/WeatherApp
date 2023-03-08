package com.example.weatherapp.model


import kotlinx.coroutines.flow.Flow

interface RepositaryInterface {

    /*============================================================================================================*/
    suspend fun getCheckCurrentsWeatherDataBase(): Welcome
    fun getCurrentsWeatherDataBase(): Flow<Welcome>
    suspend fun insertCurrentWeatherDataBase(welcome: Welcome): Long
    suspend fun updateCurrentWeatherDataBase(welcome: Welcome)
    fun getFavtsWeatherDataBase(): Flow<List<Welcome>>
    suspend fun insertFavWeatherDataBase(welcome: Welcome): Long
    suspend fun deleteFavWeatherDataBase(welcome: Welcome)
    suspend fun updateFavWeatherDataBase(welcome: Welcome)

    /*============================================================================================================*/
    suspend fun getCurrentWeatherApi(lat: String?, lon: String?): Flow<Welcome>
    suspend fun getCurrentWeatherApiForWorker(lat: String?, lon: String?): Welcome

    /*============================================================================================================*/
    fun getAlertsDataBase(): Flow<List<MyAlert>>
    suspend fun insertAlertDataBase(myAlert: MyAlert)

    suspend fun deleteAlertDataBase(myAlert: MyAlert)

}