package com.example.weatherapp.model

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.google.gson.Gson


@Dao
interface WeatherDAO{
    @Query("SELECT * FROM Welcome")
    suspend fun getFavsWeather() :MutableList<Welcome>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavWeather(welcome: Welcome): Long

    @Delete
    suspend fun deleteFavWeather(welcome: Welcome)

    @Update
    suspend fun updateFavWeather(welcome: Welcome)


}

@Database(entities = [Welcome::class], version = 6)
@TypeConverters(Conv::class)
abstract class WeatherDataBase : RoomDatabase(){

    abstract fun getFavWeatherDao() : WeatherDAO

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

