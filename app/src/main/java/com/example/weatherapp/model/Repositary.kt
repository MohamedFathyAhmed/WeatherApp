package com.example.weatherapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.CONST

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class  Repositary private constructor(var context: Context) {
    var room: WeatherDataBase
    companion object{
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

    init {
        room= WeatherDataBase.getInstance(context)
    }
/*============================================================================================================*/

//    suspend fun getCurrentWeatherDataBase():Current{
//        return room.getWeatherDao().getCurrentWeather()
//    }
//    suspend fun insertCurrentWeatherDataBase(current: Current):Long{
//        return room.getWeatherDao().insertCurrentWeather(current)
//    }


    suspend fun getFavtsWeatherDataBase(): MutableList<Welcome> {
        return room.getFavWeatherDao().getFavsWeather()
    }
    suspend fun insertFavWeatherDataBase(welcome: Welcome):Long{
         welcome.isFav=true
        return room.getFavWeatherDao().insertFavWeather(welcome)

    }
    suspend fun deleteFavWeatherDataBase(welcome: Welcome){
         room.getFavWeatherDao().deleteFavWeather(welcome)
    }
    suspend fun updateFavWeatherDataBase(welcome: Welcome){
        welcome.isFav=true
        room.getFavWeatherDao().updateFavWeather(welcome)
    }
    /*============================================================================================================*/
    suspend fun getCurrentWeatherApi( lat: String?, lon: String?): Welcome {
        val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
val units =  sharedPreference.getString(CONST.units,CONST.Enum_units.metric.toString())!!
        val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!
        return API.retrofitService.getCurrentWeather(lat,lon,units,lang)
    }

/*============================================================================================================*/

}