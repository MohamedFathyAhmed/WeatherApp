package com.example.weatherapp.view.ui.fav


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.weatherapp.CONST
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavDataViewModel(val context: Context): ViewModel(){
    private var repo: Repositary


    private  var _flowData: MutableStateFlow<ApiStateList>
     var flowData: StateFlow<ApiStateList>


    init {
        repo=Repositary.getInstance(context)

        _flowData= MutableStateFlow(ApiStateList.Loading)
        flowData= _flowData
    }



    fun getFavsWeatherDB() =
        viewModelScope.launch {
            repo.getFavtsWeatherDataBase().catch {
                    e-> _flowData.value=ApiStateList.Failure(e)
            }
                .collectLatest {
                    _flowData.value = ApiStateList.Success(it)
                }
            flowData=_flowData
        }

    fun insertFavWeatherDB(welcome: Welcome) {
        viewModelScope.launch () {
            repo.insertFavWeatherDataBase(welcome)
        }
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


