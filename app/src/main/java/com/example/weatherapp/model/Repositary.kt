package com.example.weatherapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.CONST
import com.example.weatherapp.getAddress
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow




class Repositary private constructor(
     var retrofitService: SimpleService,
     var room: ILocalDataSourceInterface,
     var context: Context
) : RepositaryInterface {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Repositary? = null
        fun getInstance(
            retrofitService: SimpleService,
            room: ILocalDataSourceInterface,
            con: Context
        ): Repositary {
            return INSTANCE ?: synchronized(this) {
                val instance = Repositary(retrofitService, room, con)
                INSTANCE = instance
                instance
            }
        }
    }

    /*============================================================================================================*/
     val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
     val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!

/*============================================================================================================*/
    override suspend fun getCheckCurrentsWeatherDataBase()= room.getCheckCurrentsWeatherDataBase()
     override fun getCurrentsWeatherDataBase()=room.getCurrentsWeatherDataBase()
    override suspend fun insertCurrentWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.insertCurrentWeatherDataBase(welcome)
    }
    override suspend fun updateCurrentWeatherDataBase(welcome: Welcome){
        welcome.isFav=0
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.updateCurrentWeatherDataBase(welcome)
    }


/*============================================================================================================*/

     override fun getFavtsWeatherDataBase()= room.getFavtsWeatherDataBase()

    override suspend fun insertFavWeatherDataBase(welcome: Welcome):Long{
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        return room.insertFavWeatherDataBase(welcome)
    }


    override suspend fun deleteFavWeatherDataBase(welcome: Welcome){
         room.deleteFavWeatherDataBase(welcome)
    }

    override suspend fun updateFavWeatherDataBase(welcome: Welcome){
        welcome.isFav=1
        welcome.timezonear= getAddress(welcome.lat,welcome.lon,"ar" ,context)
        welcome.timezone= getAddress(welcome.lat,welcome.lon,"en" ,context)
        room.updateFavWeatherDataBase(welcome)
    }
    /*============================================================================================================*/
    override suspend fun getCurrentWeatherApi(lat: String?, lon: String?)=flow<Welcome> {
        val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        val units =  sharedPreference.getString(CONST.units,CONST.Enum_units.metric.toString())!!
        val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!
        emit(retrofitService.getCurrentWeather(lat, lon, units, lang))
    }

    override suspend fun getCurrentWeatherApiForWorker(lat: String?, lon: String?): Welcome {
        val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        val units =  sharedPreference.getString(CONST.units,CONST.Enum_units.metric.toString())!!
        val lang =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!
        return retrofitService.getCurrentWeather(lat, lon, units, lang)
    }
    /*============================================================================================================*/
    override fun getAlertsDataBase()=room.getAlertsDataBase()

    override suspend fun insertAlertDataBase(myAlert: MyAlert) {
        room.insertAlertDataBase(myAlert)
    }

    override suspend fun deleteAlertDataBase(myAlert: MyAlert) {
       room.deleteAlertDataBase(myAlert)
    }
/*============================================================================================================*/

}