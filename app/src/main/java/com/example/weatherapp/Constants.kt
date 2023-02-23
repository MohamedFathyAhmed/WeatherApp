package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.weatherapp.model.TimeState
import com.example.weatherapp.model.WeatherState
import com.example.weatherapp.model.WeatherStateMap
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

fun getWeatherIcon(state: WeatherState, currentTimeState: TimeState): Int {
    return WeatherStateMap.icon[Pair(
        state,
        currentTimeState
    )] ?: WeatherStateMap.icon[Pair(
        state,
        TimeState.Undefined
    )]!!
}
object CONST {
    const val exclude= "exclude"
    const val  lat= "lat"
    const val  lon= "lon"
    const val  appid= "appid"
    const val units =" units"
    const val onecall = "onecall"
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val UNITS = "metric"
    val DAYS_OF_WEEK = arrayOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )
    val MONTH_NAME = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    val DAYS_OF_WEEK_PERSIAN = arrayOf(
        "یکشنبه",
        "دوشنبه",
        "سه‌شنبه",
        "چهارشنبه",
        "پنج‌شنبه",
        "جمعه",
        "شنبه"
    )
    val MONTH_NAME_PERSIAN = arrayOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )
    val WEATHER_STATUS = arrayOf(
        "Thunderstorm",
        "Drizzle",
        "Rain",
        "Snow",
        "Atmosphere",
        "Clear",
        "Few Clouds",
        "Broken Clouds",
        "Cloud"
    )
    val WEATHER_STATUS_PERSIAN = arrayOf(
        "رعد و برق",
        "نمنم باران",
        "باران",
        "برف",
        "جو ناپایدار",
        "صاف",
        "کمی ابری",
        "ابرهای پراکنده",
        "ابری"
    )
    const val CITY_INFO = "city-info"
    const val TIME_TO_PASS = (6 * 600000).toLong()
    const val LAST_STORED_CURRENT = "last-stored-current"
    const val LAST_STORED_MULTIPLE_DAYS = "last-stored-multiple-days"
   const val API_KEY = "bec88e8dd2446515300a492c3862a10e"
    const val LANGUAGE = "language"
    const val DARK_THEME = "dark-theme"
    const val FIVE_DAY_WEATHER_ITEM = "five-day-weather-item"




}