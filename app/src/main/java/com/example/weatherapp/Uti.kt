package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

class Uti {
}

// convert to hours
@SuppressLint("SimpleDateFormat")
fun convertToTime(dt: Long, context: Context): String {
    val date = Date(dt * 1000)
    val format = SimpleDateFormat("h:mm a", Locale("en"))
    return format.format(date)
}


// now Day friday
fun convertToDay(dt: Long): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dt
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
}

// return now day history
fun convertToDate(dt: Long, context: Context): String {
    val date = Date(dt * 1000)
    val format = SimpleDateFormat("d MMM, yyyy", Locale("en"))
    return format.format(date)
}

fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}



