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
    private  var _dailyList: MutableLiveData<List<Daily>>
     var dailyList: LiveData<List<Daily>>
    private  var _welcome: MutableLiveData<Welcome>
     var welcome: LiveData<Welcome>
    init {
        repo=Repositary.getInstance(context)
        _current= MutableLiveData()
        current= _current
        _dailyList= MutableLiveData()
        dailyList= _dailyList
        _welcome= MutableLiveData()
        welcome= _welcome

    }

    fun getCurrentWeatherApi(lat: String?, lon: String?, exclude: String?, appId: String= CONST.API_KEY, units: String="metric") {
        viewModelScope.launch {
            var Welcome =   repo.getCurrentWeatherApi(lat,lon,exclude,appId,units)
            _current.value=Welcome.current
            current=_current

            _dailyList.value=Welcome.daily
            dailyList=_dailyList

           _welcome.value=Welcome
            welcome=_welcome

        }
    }


}


