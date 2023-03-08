package com.example.weatherapp.view.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repositary

class NotificationsViewModelFactory(var repo: Repositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            NotificationsViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}