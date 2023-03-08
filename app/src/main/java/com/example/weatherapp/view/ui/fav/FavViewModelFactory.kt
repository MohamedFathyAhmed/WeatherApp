package com.example.weatherapp.view.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repositary

class FavViewModelFactory(var repo: Repositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavDataViewModel::class.java)) {
            FavDataViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}