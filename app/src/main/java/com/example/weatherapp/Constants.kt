package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


object CONST {
    const val exclude= "exclude"
    const val  lat= "lat"
    const val  lon= "lon"
    const val  appid= "appid"

    const val onecall = "onecall"

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

   const val API_KEY = "bec88e8dd2446515300a492c3862a10e"

    const val Background = "Background"
//get SharedPreferences
//    val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
//    sharedPreference.edit().putString("username","Anupam").commit()
//set SharedPreferences
//val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
//   sharedPreference.getString("username","defaultName")

    const val LOCATION = "LOCATION"
    enum class Enum_LOCATION(){map,gps}
    const val FavLong = "GpsLong"
    const val FavLat = "GpsLat"
    const val MapLong = "MapLong"
    const val MapLat = "MapLat"
    const val units =" units"
    enum class Enum_units(){standard,metric,imperial}
    const val lang ="lang"
    enum class Enum_language(){ar,en}


}

