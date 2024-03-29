
package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.*

class FakeDataSourceDB(var data: MutableList<Welcome> = mutableListOf()) :ILocalDataSourceInterface {
    override suspend fun getCheckCurrentsWeatherDataBase(): Welcome {
        TODO("Not yet implemented")
    }

    override fun getCurrentsWeatherDataBase(): Flow<Welcome> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentWeatherDataBase(welcome: Welcome): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrentWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
    }
    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    override fun getFavtsWeatherDataBase(): Flow<List<Welcome>> {
        val flowOfLists: Flow<List<Welcome>> = flowOf(data)
        return flowOfLists
       // TODO("Not yet implemented")
    }

    override suspend fun insertFavWeatherDataBase(welcome: Welcome): Long {
        data.add(welcome)
        return 1
    }

    override suspend fun deleteFavWeatherDataBase(welcome: Welcome) {
        data.remove(welcome)
    }

    override suspend fun updateFavWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
    }

    override fun getAlertsDataBase(): Flow<List<MyAlert>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlertDataBase(myAlert: MyAlert) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlertDataBase(myAlert: MyAlert) {
        TODO("Not yet implemented")
    }


}