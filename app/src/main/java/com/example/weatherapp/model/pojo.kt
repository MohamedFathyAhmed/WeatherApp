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

data class Current (
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
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
enum class WeatherState {
    ClearSky, FewClouds, ScatteredClouds, BrokenClouds, ShowerRain, Rain, Thunderstorm, Snow, Mist
}

object WeatherStateMap {
    val icon = mutableMapOf<Pair<WeatherState, TimeState>, Int>()
    init {
        icon += Pair(WeatherState.ClearSky, TimeState.Morning) to R.drawable.ic_clear_sky_morning
        icon += Pair(WeatherState.ClearSky, TimeState.Night) to R.drawable.ic_clear_sky_night
        icon += Pair(WeatherState.ClearSky, TimeState.Dawn) to R.drawable.ic_clear_sky_dawn
        icon += Pair(WeatherState.ClearSky, TimeState.Evening) to R.drawable.ic_clear_sky_evening
        icon += Pair(WeatherState.ClearSky, TimeState.Undefined) to R.drawable.ic_clear_sky_morning

        icon += Pair(WeatherState.FewClouds, TimeState.Morning) to R.drawable.ic_few_cloud_morning
        icon += Pair(WeatherState.FewClouds, TimeState.Night) to R.drawable.ic_few_cloud_night
        icon += Pair(WeatherState.FewClouds, TimeState.Dawn) to R.drawable.ic_few_cloud_dawn
        icon += Pair(WeatherState.FewClouds, TimeState.Evening) to R.drawable.ic_few_cloud_evening
        icon += Pair(WeatherState.FewClouds, TimeState.Undefined) to R.drawable.ic_few_cloud_morning

        icon += Pair(WeatherState.ScatteredClouds, TimeState.Undefined) to R.drawable.ic_scattered_clouds
        icon += Pair(WeatherState.Rain, TimeState.Undefined) to R.drawable.ic_rain
        icon += Pair(WeatherState.Mist, TimeState.Undefined) to R.drawable.ic_mist
        icon += Pair(WeatherState.ShowerRain, TimeState.Undefined) to R.drawable.ic_shower_raint
        icon += Pair(WeatherState.Thunderstorm, TimeState.Undefined) to R.drawable.ic_thunderstorm
        icon += Pair(WeatherState.Snow, TimeState.Undefined) to R.drawable.ic_snow
        icon += Pair(WeatherState.BrokenClouds, TimeState.Undefined) to R.drawable.ic_broken_cloud
    }
}