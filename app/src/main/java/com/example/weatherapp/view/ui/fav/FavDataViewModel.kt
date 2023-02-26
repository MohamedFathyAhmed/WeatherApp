package com.example.weatherapp.view.ui.fav


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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary


    private  var _welcome: MutableLiveData<Welcome>
    var welcome: LiveData<Welcome>

    private  var _favs: MutableLiveData<MutableList<Welcome>>
    var favs: LiveData<MutableList<Welcome>>

    init {
        repo=Repositary.getInstance(context)
        _favs= MutableLiveData()
        favs= _favs

        _welcome= MutableLiveData()
        welcome= _welcome
    }



    fun getFavsWeatherDB() {
        viewModelScope.launch {
            var Root =   repo.getFavtsWeatherDataBase()
            _favs.value=Root
            favs=_favs

        }
    }

    fun insertFavWeatherDB(welcome: Welcome) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.insertFavWeatherDataBase(welcome)
        }
        getFavsWeatherDB()
    }

    fun deleteFavWeatherDB(welcome: Welcome) {
        viewModelScope.launch {
            repo.deleteFavWeatherDataBase(welcome)
        }
    }
    fun updateFavWeatherDB(welcome: Welcome) {
        viewModelScope.launch {
            repo.updateFavWeatherDataBase(welcome)
        }
    }


}


