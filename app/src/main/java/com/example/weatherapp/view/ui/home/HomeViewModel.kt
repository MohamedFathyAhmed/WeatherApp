package com.example.weatherapp.view.ui.home

import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary


    private  var _data: MutableStateFlow<ApiState>
     var data: StateFlow<ApiState>


    init {
        repo=Repositary.getInstance(context)

        _data= MutableStateFlow(ApiState.Loading)
        data= _data


    }



    fun getCurrentWeatherDB() =
        viewModelScope.launch {
            repo.getCurrentsWeatherDataBase().catch {
                    e-> _data.value=ApiState.Failure(e)
            }
                .collectLatest {
                    _data.value = ApiState.Success(it)
                }
            data=_data
        }

        fun insertCurrentWeatherDB(welcome: Welcome) {

               viewModelScope.launch {
                   repo.insertCurrentWeatherDataBase(welcome)

           }


    }


     fun checkCurrentWeatherDB()=
        viewModelScope.launch {
              repo.getCheckCurrentsWeatherDataBase()
        }



    fun updateCurrentWeatherDB(welcome: Welcome) {
        viewModelScope.launch {
            repo.updateCurrentWeatherDataBase(welcome)
        }
    }


    fun getCurrentWeatherApi(lat: String?, lon: String?) =
        viewModelScope.launch {
          repo.getCurrentWeatherApi(lat,lon).catch {
                    e-> _data.value=ApiState.Failure(e)
            }
                .collectLatest {
                    _data.value = ApiState.Success(it)
                }

            data=_data

        }
    }




