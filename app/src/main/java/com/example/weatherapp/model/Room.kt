package com.example.weatherapp.model

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDAO{
    @Query("SELECT * FROM Welcome WHERE isFav = 1")
     fun getFavsWeather() : Flow<List<Welcome>>

    @Query("SELECT * FROM Welcome WHERE isFav = 0 ORDER BY timezone_offset ASC")
     fun getCurrentWeather() :Flow<Welcome>

    @Query("SELECT * FROM Welcome WHERE isFav = 0")
    suspend  fun getCheckCurrentWeather() :Welcome

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
    @Query("SELECT * FROM MyAlert")
     fun getAlerts(): Flow<List<MyAlert>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(myAlert: MyAlert): Long

    @Delete
    suspend fun deleteAlerts(myAlert: MyAlert)

}

@Database(entities = [Welcome::class,MyAlert::class], exportSchema = false, version = 4)
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

