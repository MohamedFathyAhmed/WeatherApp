package com.example.weatherapp.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Repositary
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.launch


class HomeDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary
    private  var _current: MutableLiveData<Current>
    var current: LiveData<Current>
    private lateinit var _dailyList: MutableLiveData<List<Daily>>
    lateinit var dailyList: LiveData<List<Daily>>
    private lateinit var _welcome: MutableLiveData<Welcome>
    lateinit var welcome: LiveData<Welcome>
    init {
        repo=Repositary.getInstance(context)
        _current= MutableLiveData()
        current= _current
        _dailyList= MutableLiveData()
        dailyList= _dailyList
        _welcome= MutableLiveData()
        welcome= _welcome
        getCurrentWeatherApi()


    }


    fun getCurrentWeatherApi() {
        viewModelScope.launch {
            var Welcome =   repo.getCurrentWeatherApi("33.44","-94.04","minutely")
            _current.value=Welcome.current
            current=_current

            _dailyList.value=Welcome.daily
            dailyList=_dailyList

           _welcome.value=Welcome
            welcome=_welcome

        }
    }


}

class DataViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        return if (modelClass.isAssignableFrom(HomeDataViewModel::class.java))
        {
            HomeDataViewModel(context) as T
        }
        else{
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}
