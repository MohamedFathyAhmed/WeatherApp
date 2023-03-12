package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class MyAlert(
    var Time: Long,
    var startDay: Long,
    var endDay: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var lat: Double,
    var lon: Double,
    var AlertCityName :String
)
@Entity(primaryKeys = ["isFav","timezone"])
data class Welcome (
    var isFav:Int=0,
    val lat: Double=0.0,
    val lon: Double=0.0,
    var timezone: String="",
    var timezonear: String="",
    val timezone_offset: Long=0,
    val current: Current?,
    val hourly: List<Current>?,
    val daily: List<Daily>?,
    val alerts: List<Alert>?
):java.io.Serializable

data class Condition(
    var img: Int,
    var des: String,
    var name: String
)
data class Alert (
    val senderName: String? = null,
    val event: String? = null,
    val start: Long? = null,
    val end: Long? = null,
    val description: String? = null,
    val tags: List<String> = emptyList()
)


data class Current (
    val dt: Long=0,
    val sunrise: Long=0,
    val sunset: Long=0,
    var temp: Double=0.0,
    val feelsLike: Double=0.0,
    val pressure: Long=0,
    val humidity: Long=0,
    val dewPoint: Double=0.0,
    val uvi: Double=0.0,
    val clouds: Long=0,
    val visibility: Long=0,
    val wind_speed: Double=0.0,
    val wind_Deg: Long=0,
    val wind_Gust: Double=0.0,
    val weather: List<Weather> = emptyList(),
    val pop: Double=0.0
):java.io.Serializable



data class Weather (
    val id: Long,
    val description: String,
    val icon: String
)


data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Long,
    val pop: Double,
    val uvi: Double,
    val rain: Double,
    val snow: Double
):java.io.Serializable

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)


class Conv {
    @TypeConverter
    fun fromCurrentToString(current: Current) = Gson().toJson(current)
    @TypeConverter
    fun fromStringToCurrent(stringCurrent : String) = Gson().fromJson(stringCurrent, Current::class.java)

    @TypeConverter
    fun fromWeatherToString(weather: List<Weather>) = Gson().toJson(weather)
    @TypeConverter
    fun fromStringToWeather(stringCurrent : String) = Gson().fromJson(stringCurrent, Array<Weather>::class.java).toList()

    @TypeConverter
    fun fromDailyListToString(daily: List<Daily>) = Gson().toJson(daily)
    @TypeConverter
    fun fromStringToDailyList(stringDaily : String) = Gson().fromJson(stringDaily, Array<Daily>::class.java).toList()

    @TypeConverter
    fun fromHourlyListToString(hourly: List<Current>) = Gson().toJson(hourly)
    @TypeConverter
    fun fromStringToHourlyList(stringHourly : String) = Gson().fromJson(stringHourly, Array<Current>::class.java).toList()

    @TypeConverter
    fun fromAlertsToString(alerts: List<Alert>?): String {
        if (!alerts.isNullOrEmpty()) {
            return Gson().toJson(alerts)
        }
        return ""
    }
    @TypeConverter
    fun fromStringToAlerts(alerts: String?): List<Alert> {
        if (alerts.isNullOrEmpty()) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Alert?>?>() {}.type
        return Gson().fromJson(alerts, listType)
    }
    @TypeConverter
    fun fromWelcomeToString(welcome: Welcome) = Gson().toJson(welcome)
    @TypeConverter
    fun fromStringToWelcome(stringCurrent : String) = Gson().fromJson(stringCurrent, Welcome::class.java)


}

