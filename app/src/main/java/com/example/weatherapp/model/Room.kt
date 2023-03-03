package com.example.weatherapp.model

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDAO{
    @Query("SELECT * FROM Welcome WHERE isFav = 1")
     fun getFavsWeather() : Flow<List<Welcome>>

    @Query("SELECT * FROM Welcome WHERE isFav = 0")
     fun getCurrentWeather() :Flow<Welcome>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavWeather(welcome: Welcome): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrentWeather(welcome: Welcome): Long

    @Delete
    suspend fun deleteFavWeather(welcome: Welcome)

    @Update
    suspend fun updateFavWeather(welcome: Welcome)

    @Update
    suspend fun updateCurrentWeather(welcome: Welcome)


}


@Dao
interface AlertDAO {
    @Query("SELECT * FROM Alert")
     fun getAlerts(): Flow<List<Alert>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(alert: Alert): Long

    @Delete
    suspend fun deleteAlerts(alert: Alert)

}

@Database(entities = [Welcome::class,Alert::class], version = 12)
@TypeConverters(Conv::class)
abstract class WeatherDataBase : RoomDatabase(){

    abstract fun getFavWeatherDao() : WeatherDAO
    abstract fun getAlertDao() : AlertDAO

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

