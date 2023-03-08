package com.example.weatherapp.view.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repositary

class HomeViewModelFactory(var repo: Repositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeDataViewModel::class.java)) {
            HomeDataViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}