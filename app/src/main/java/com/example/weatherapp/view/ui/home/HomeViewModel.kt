package com.example.weatherapp.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.weatherapp.CONST
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Repositary
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.launch


class HomeDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary

    private  var _current: MutableLiveData<Current>
     var current: LiveData<Current>
    private  var _welcome: MutableLiveData<Welcome>
     var welcome: LiveData<Welcome>


    init {
        repo=Repositary.getInstance(context)


        _current= MutableLiveData()
        current= _current

        _welcome= MutableLiveData()
        welcome= _welcome

    }

//    fun getCurrentWeatherDB() {
//        viewModelScope.launch {
//            var Root =   repo.getCurrentWeatherDataBase()
//            _current.value=Root
//            current=_current
//
//        }
//    }
//
//    fun insertCurrentWeatherDB(current: Current) {
//        viewModelScope.launch {
//            repo.insertCurrentWeatherDataBase(current)
//        }
//    }


    fun getCurrentWeatherApi(lat: String?, lon: String?) {
        viewModelScope.launch {
            var Welcome =   repo.getCurrentWeatherApi(lat,lon)
           _welcome.value=Welcome
            welcome=_welcome

        }
    }


}


