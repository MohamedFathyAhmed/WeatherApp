package com.example.weatherapp.model
//:java.io.Serializable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity
data class Welcome (
    var isFav:Int=0,
    val lat: Double,
    val lon: Double,
    @PrimaryKey
    var timezone: String,
    var timezonear: String="",
    val timezone_offset: Long,
    val current: Current,
    val hourly: List<Current>,
    val daily: List<Daily>
):java.io.Serializable

data class Condition(
    var img: Int,
    var des: String,
    var name: String
)

data class Current (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    var temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val wind_speed: Double,
    val wind_Deg: Long,
    val wind_Gust: Double,
    val weather: List<Weather>,
    val pop: Double
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
    fun fromweatherToString(weather: Weather) = Gson().toJson(weather)
    @TypeConverter
    fun fromStringToweather(stringCurrent : String) = Gson().fromJson(stringCurrent, Weather::class.java)

    @TypeConverter
    fun fromDailyListToString(daily: List<Daily>) = Gson().toJson(daily)
    @TypeConverter
    fun fromStringToDailyList(stringDaily : String) = Gson().fromJson(stringDaily, Array<Daily>::class.java).toList()

    @TypeConverter
    fun fromHourlyListToString(hourly: List<Current>) = Gson().toJson(hourly)
    @TypeConverter
    fun fromStringToHourlyList(stringHourly : String) = Gson().fromJson(stringHourly, Array<Current>::class.java).toList()


}
@Entity
data class Alert(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var startTime: Long,
    var endTime: Long,
    var startDay: Long,
    var endDay: Long
)
