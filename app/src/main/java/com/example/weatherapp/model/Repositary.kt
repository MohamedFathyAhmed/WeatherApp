package com.example.weatherapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.CONST


class  Repositary private constructor(var context: Context) {

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

    suspend fun getCurrentWeatherApi( lat: String?, lon: String?): Welcome {
        val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
val units =  sharedPreference.getString(CONST.units,CONST.Enum_units.metric.toString())
        val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())
        return API.retrofitService.getCurrentWeather(lat,lon,units,lang)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

}