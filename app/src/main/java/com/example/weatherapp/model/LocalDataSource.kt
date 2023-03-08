package com.example.weatherapp.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.getAddress


class LocalDataSource(var room: WeatherDataBase,var context: Context) : ILocalDataSourceInterface {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(
             room: WeatherDataBase, context: Context
        ): LocalDataSource {
            return INSTANCE ?: synchronized(this) {
                val instance = LocalDataSource( room, context)
                INSTANCE = instance
                instance
            }
        }
    }

     override suspend fun getCheckCurrentsWeatherDataBase()= room.getFavWeatherDao().getCheckCurrentWeather()
     override fun getCurrentsWeatherDataBase()=room.getFavWeatherDao().getCurrentWeather()
     override suspend fun insertCurrentWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.getFavWeatherDao().insertCurrentWeather(welcome)
    }
     override suspend fun updateCurrentWeatherDataBase(welcome: Welcome){
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.getFavWeatherDao().updateCurrentWeather(welcome)
    }


/*============================================================================================================*/

     override fun getFavtsWeatherDataBase()= room.getFavWeatherDao().getFavsWeather()

     override suspend fun insertFavWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.getFavWeatherDao().insertFavWeather(welcome)
    }


     override suspend fun deleteFavWeatherDataBase(welcome: Welcome){
        room.getFavWeatherDao().deleteFavWeather(welcome)
    }

     override suspend fun updateFavWeatherDataBase(welcome: Welcome){
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.getFavWeatherDao().updateFavWeather(welcome)
    }

     override fun getAlertsDataBase()=room.getAlertDao().getAlerts()

     override suspend fun insertAlertDataBase(myAlert: MyAlert) {
        room.getAlertDao().insertAlert(myAlert)
    }

     override suspend fun deleteAlertDataBase(myAlert: MyAlert) {
        room.getAlertDao().deleteAlerts(myAlert)
    }

}