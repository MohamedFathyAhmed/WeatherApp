package com.example.weatherapp.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.CONST
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Repositary
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary


    private lateinit var _welcome: MutableStateFlow<ApiState>
     lateinit var welcome: StateFlow<ApiState>


    init {
        repo=Repositary.getInstance(context)

        _welcome= MutableStateFlow(ApiState.Loading)
        welcome= _welcome

    }




    fun getCurrentWeatherApi(lat: String?, lon: String?) =
        viewModelScope.launch {

              repo.getCurrentWeatherApi(lat,lon).catch {
                      e-> _welcome.value=ApiState.Failure(e)
                }
                   .collectLatest {
                    _welcome.value = ApiState.Success(it)

                }

        }


}

sealed class ApiState {
    class Success(val data: Welcome) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
}


