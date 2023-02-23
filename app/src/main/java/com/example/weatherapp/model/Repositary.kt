package com.example.weatherapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.CONST
import retrofit2.http.Query


class  Repositary private constructor(context: Context) {

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

    suspend fun getCurrentWeatherApi( lat: String?, lon: String?,exclude: String?, appId: String= CONST.API_KEY, units: String): Welcome {
        return API.retrofitService.getCurrentWeather(lat,lon,exclude,appId,  units)
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