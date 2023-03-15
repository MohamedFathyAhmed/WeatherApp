package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*


fun getAddress(latitude: Double, longitude: Double, lang:String, context: Context): String {
    var address = ""
    val geocoder = Geocoder(context,  Locale(lang))
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    if (addresses != null && addresses.size > 0) {
        if(addresses.get(0).adminArea!=null) {
            address = addresses.get(0).adminArea
        }
    }
    return address
}




fun getCurrentTemperature(context: Context): String {
    val sharedPreference =  context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
  sharedPreference.getString(CONST.units,CONST.Enum_units.standard.toString())
    return when (  sharedPreference.getString(CONST.units,CONST.Enum_units.standard.toString())) {
        CONST.Enum_units.metric.toString() -> {
            context.getString(R.string.Celsusi)
        }
        CONST.Enum_units.imperial.toString()-> {
            context.getString(R.string.Ferherhit)
        }
        CONST.Enum_units.standard.toString() -> {
            context.getString(R.string.KELVIN)
        }
        else -> {
            context.getString(R.string.Celsusi)
        }
    }
}


fun getCurrentSpeed(context: Context): String {
    val sharedPreference =  context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    return when (  sharedPreference.getString(CONST.units,CONST.Enum_units.standard.toString())) {
        CONST.Enum_units.metric.toString() -> {
            context.getString(R.string.MeterPerSecond)
        }
        CONST.Enum_units.imperial.toString()-> {
            context.getString(R.string.MiliPerHour)
        }
        CONST.Enum_units.standard.toString() -> {
            context.getString(R.string.MeterPerSecond)
        }
        else -> {
            context.getString(R.string.MeterPerSecond)
        }
    }
}


@SuppressLint("SuspiciousIndentation")
fun setBackgroundContainer(timeState: String, context: Context) :Int{
    val sharedPreference =  context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val iconValue: Int

    when (timeState) {
        "02n" ->{
            iconValue =     R.drawable.background_dawn
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "01n" ->{
            iconValue =     R.drawable.background_night
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "01d"-> {
            iconValue =    R.drawable.background_morning
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "04d" ->{
            iconValue =    R.drawable.background_evening
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "13n" ->{
            iconValue =    R.drawable.background_evening
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "13d" ->{
            iconValue =    R.drawable.background_evening
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "10d" ->{
            iconValue =   R.drawable.background_noon
            sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        else -> {
            iconValue = R.drawable.background_evening
            sharedPreference.edit().putInt(CONST.Background, iconValue).commit()
        }

    }

    return iconValue
}

@SuppressLint("SuspiciousIndentation")
fun setLotte(timeState: String, context: Context) :Int{
    val sharedPreference =  context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val iconValue: Int

    when (timeState) {
        "13d" ->{
            iconValue =     R.raw.snow
          //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "13n" ->{
            iconValue =     R.raw.snow
            //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "09d" ->{
            iconValue =     R.raw.rain
          //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "10d" ->{
            iconValue =     R.raw.rain
            //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "09n" ->{
            iconValue =     R.raw.rain
            //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }
        "10n" ->{
            iconValue =     R.raw.rain
            //  sharedPreference.edit().putInt(CONST.Background,iconValue).commit()
        }

        else -> {
            iconValue = 0
        //    sharedPreference.edit().putInt(CONST.Background, iconValue).commit()
        }

    }

    return iconValue
}

fun getIconImage(icon: String): Int {
    val iconValue: Int
    when (icon) {
        "01d" -> iconValue = R.drawable.ic_clear_sky_morning
        "01n" -> iconValue = R.drawable.ic_clear_sky_dawn
        "02d" -> iconValue = R.drawable.ic_few_cloud_morning
        "02n" -> iconValue = R.drawable.ic_few_cloud_night
        "03n" -> iconValue = R.drawable.ic_scattered_clouds
        "03d" -> iconValue = R.drawable.ic_scattered_clouds
        "04d" -> iconValue = R.drawable.ic_broken_cloud
        "04n" -> iconValue = R.drawable.ic_broken_cloud
        "09d" -> iconValue = R.drawable.ic_shower_raint
        "09n" -> iconValue = R.drawable.ic_shower_raint
        "10d" -> iconValue = R.drawable.ic_rain
        "10n" -> iconValue = R.drawable.ic_rain
        "11d" -> iconValue = R.drawable.ic_thunderstorm
        "11n" -> iconValue = R.drawable.ic_thunderstorm
        "13d" -> iconValue = R.drawable.ic_snow
        "13n" -> iconValue = R.drawable.ic_snow
        "50d" -> iconValue = R.drawable.ic_mist
        "50n" -> iconValue = R.drawable.ic_mist
        else -> iconValue = R.drawable.ic_humidity
    }
    return iconValue
}


fun convertStringToArabic(value: String): String {
    return (value + "")
        .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
        .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
        .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
        .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
        .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
}
// convert to hours
@SuppressLint("SimpleDateFormat")
fun convertToTime(dt: Long,lang :String): String {
    val date = Date(dt * 1000)
    val format = SimpleDateFormat("h:mm a", Locale(lang))
    return format.format(date)
}



fun convertToDay(dt: Long,lang :String): String? {
    val date = Date(dt * 1000)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val en = Locale.ENGLISH
    val ar = Locale("ar", "SA")
    var location:Locale
    if (lang.equals("ar")) {
         location = ar
    }else{
        location =en
    }
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG,location )
}


fun convertToDate(dt: Long,lang :String): String {
    val date = Date(dt * 1000)
    val format = SimpleDateFormat("d MMM, yyyy", Locale(lang))
    return format.format(date)
}

fun getseconds(year: Int, month: Int, day: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)
    return calendar.timeInMillis / 1000
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










