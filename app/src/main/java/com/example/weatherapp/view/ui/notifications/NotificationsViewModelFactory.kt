package com.example.weatherapp.view.ui.notifications

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class  NotificationsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        return if (modelClass.isAssignableFrom(NotificationsViewModel::class.java))
        {
            NotificationsViewModel(context) as T
        }
        else{
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}