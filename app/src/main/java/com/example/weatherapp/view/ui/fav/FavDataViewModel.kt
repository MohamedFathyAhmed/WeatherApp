package com.example.weatherapp.view.ui.fav


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ApiStateList
import com.example.weatherapp.model.Repositary
import com.example.weatherapp.model.RepositaryInterface
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavDataViewModel(var repo: RepositaryInterface) : ViewModel() {


    private var _flowData: MutableStateFlow<ApiStateList>
    var flowData: StateFlow<ApiStateList>


    init {

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


