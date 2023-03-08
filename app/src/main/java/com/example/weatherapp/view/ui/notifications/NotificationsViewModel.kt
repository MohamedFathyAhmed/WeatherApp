package com.example.weatherapp.view.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ApiStateAlert
import com.example.weatherapp.model.MyAlert
import com.example.weatherapp.model.Repositary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationsViewModel(var repo: Repositary) : ViewModel() {


    private var _flowData: MutableStateFlow<ApiStateAlert>
    var flowData: StateFlow<ApiStateAlert>


    init {

        _flowData = MutableStateFlow(ApiStateAlert.Loading)
            flowData= _flowData
        }



        fun getAlertsDB() =
            viewModelScope.launch {
                repo.getAlertsDataBase().catch {
                        e-> _flowData.value= ApiStateAlert.Failure(e)
                }
                    .collectLatest {
                        _flowData.value = ApiStateAlert.Success(it)
                    }
                flowData=_flowData
            }

        fun insertAlertDB(myAlert: MyAlert) {
            viewModelScope.launch () {
                repo.insertAlertDataBase(myAlert)
            }
        }

        fun deleteAlertDB(myAlert: MyAlert) {
            viewModelScope.launch {
                repo.deleteAlertDataBase(myAlert)
            }
        }



    }



