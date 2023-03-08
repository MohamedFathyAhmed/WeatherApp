/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.Flow

class FakeDataSourceDB(var tasks: MutableList<Welcome>? = mutableListOf()) :ILocalDataSourceInterface {
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

    override fun getFavtsWeatherDataBase(): Flow<List<Welcome>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavWeatherDataBase(welcome: Welcome): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavWeatherDataBase(welcome: Welcome) {
        TODO("Not yet implemented")
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