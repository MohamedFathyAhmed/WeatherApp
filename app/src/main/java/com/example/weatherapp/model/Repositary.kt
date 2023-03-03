package com.example.weatherapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.CONST
import com.example.weatherapp.getAddress

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow


class  Repositary private constructor(var context: Context) {
    var room: WeatherDataBase
    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Repositary? = null
        fun getInstance (ctx: Context): Repositary{
            return INSTANCE ?: synchronized(this) {
                val instance =Repositary(ctx)
                INSTANCE = instance
                instance }
        }
    }

    /*============================================================================================================*/
    val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val units =  sharedPreference.getString(CONST.units,CONST.Enum_units.metric.toString())!!
    val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!
    init {
        room= WeatherDataBase.getInstance(context)
    }
/*============================================================================================================*/

     fun getCurrentsWeatherDataBase()=room.getFavWeatherDao().getCurrentWeather()
    suspend fun insertCurrentWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.getFavWeatherDao().insertCurrentWeather(welcome)
    }
    suspend fun updateCurrentWeatherDataBase(welcome: Welcome){
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.getFavWeatherDao().updateCurrentWeather(welcome)
    }


/*============================================================================================================*/

     fun getFavtsWeatherDataBase()= room.getFavWeatherDao().getFavsWeather()

    suspend fun insertFavWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.getFavWeatherDao().insertFavWeather(welcome)
    }


    suspend fun deleteFavWeatherDataBase(welcome: Welcome){
         room.getFavWeatherDao().deleteFavWeather(welcome)
    }

    suspend fun updateFavWeatherDataBase(welcome: Welcome){
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.getFavWeatherDao().updateFavWeather(welcome)
    }
    /*============================================================================================================*/
    suspend fun getCurrentWeatherApi( lat: String?, lon: String?)=flow<Welcome> {
        emit( API.retrofitService.getCurrentWeather(lat,lon,units,lang))

    }
    /*============================================================================================================*/
    suspend fun getAlertsDataBase()=room.getAlertDao().getAlerts()

    suspend fun insertAlertDataBase(alert: Alert) {
        room.getAlertDao().insertAlert(alert)
    }

    suspend fun deleteAlertDataBase(alert: Alert) {
       room.getAlertDao().deleteAlerts(alert)
    }
/*============================================================================================================*/

}