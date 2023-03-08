package com.example.weatherapp.view.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ApiState
import com.example.weatherapp.model.Repositary
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeDataViewModel(var repo: Repositary) : ViewModel() {


    private var _data: MutableStateFlow<ApiState>
    var data: StateFlow<ApiState>


    init {

        _data = MutableStateFlow(ApiState.Loading)
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
                    println(it)
                }

            data=_data

        }
    }




