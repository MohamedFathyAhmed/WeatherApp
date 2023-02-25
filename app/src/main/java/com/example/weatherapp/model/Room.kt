package com.example.weatherapp.model

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.google.gson.Gson

@Dao
interface WeatherDAO{

    @Query("SELECT * FROM Current")
    suspend fun getCurrentWeather() :Current

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrentWeather(current: Current): Long

}
@Dao
interface FavWeatherDAO{
    @Query("SELECT * FROM Welcome")
    suspend fun getFavsWeather() :List<Welcome>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavWeather(welcome: Welcome): Long

}

@Database(entities = [Welcome::class,Current::class], version = 5)
@TypeConverters(Conv::class)
abstract class WeatherDataBase : RoomDatabase(){
    abstract fun getWeatherDao() : WeatherDAO
    abstract fun getFavWeatherDao() : FavWeatherDAO

    companion object{
        private var instance : WeatherDataBase? =null
        @Synchronized
        fun getInstance(context: Context) : WeatherDataBase{
            return instance?: Room.databaseBuilder(
                context.applicationContext,
                WeatherDataBase::class.java,
                "DB"
            ).fallbackToDestructiveMigration().build()
        }
    }
}

