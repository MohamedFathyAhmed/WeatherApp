package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings.Secure.getString
import com.example.weatherapp.model.Current

import com.example.weatherapp.model.Weather

import com.example.weatherapp.model.Welcome
import java.text.SimpleDateFormat
import java.util.*



fun setBackgroundContainer(timeState: String) :Int{
    val iconValue: Int

        when (timeState) {
            "02n" ->
                iconValue =     R.drawable.background_dawn
            "01n" ->
                iconValue =     R.drawable.background_night
            "01d"->
                iconValue =    R.drawable.background_morning
            "04d" ->
                iconValue =    R.drawable.background_evening
            "10d" ->
                iconValue =   R.drawable.background_noon
            else ->
                iconValue =  R.drawable.background_morning
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

fun convertIntToArabic(input: Int): String {
    val arabicNumber = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")

    var result = ""

    var number = input

    while (number > 0) {
        val digit = number % 10

        result = arabicNumber[digit] + result

        number /= 10
    }

    return result
}

fun convertDoubleToArabic(input: Double): String {
    val arabicNumber = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")

    var result = ""

    var number = input.toLong()

    while (number > 0) {
        val digit = number % 10

        result = arabicNumber[digit.toInt()] + result

        number /= 10
    }

    // add decimal part if needed
    if (input - input.toLong() > 0) {
        result += '.'

        var decimalPart = (input - input.toLong()) * 100

        while (decimalPart > 0) {
            val digit = decimalPart % 10

            result += arabicNumber[digit.toInt()]

            decimalPart /= 10
        }
    }

    return result
}
fun convertAntToArabic(input: Any): Any {
    return when (input) {
        is Int -> convertIntToArabic(input)
        is Double -> convertDoubleToArabic(input)
        else -> input
    }
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




