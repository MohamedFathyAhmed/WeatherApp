package com.example.weatherapp.view.ui.fav

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.view.ui.home.HomeDataViewModel

class  FavViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        return if (modelClass.isAssignableFrom(FavDataViewModel::class.java))
        {
            FavDataViewModel(context) as T
        }
        else{
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}