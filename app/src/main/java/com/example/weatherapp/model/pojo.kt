package com.example.weatherapp.model
//:java.io.Serializable
import com.example.weatherapp.R


data class Welcome (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: Current,
    val hourly: List<Current>,
    val daily: List<Daily>
)

data class Condition(
    var img: Int,
    var des: String,
    var name: String
)

data class Current (
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
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
    val pop: Double? = null,
    val rain: Rain? = null
)

data class Rain (
    val the1H: Double
)

data class Weather (
    val id: Long,
    val main: Main,
    val description: String,
    val icon: String
)



enum class Main {
    Clear,
    Clouds,
    Rain,
    Snow
}

data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val feelsLike: FeelsLike,
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
    val rain: Double? = null,
    val snow: Double? = null
)

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

data class Minutely (
    val dt: Long,
    val precipitation: Long
)


enum class TimeState {
    Dawn, Morning, Noon, Evening, Night, Undefined
}
