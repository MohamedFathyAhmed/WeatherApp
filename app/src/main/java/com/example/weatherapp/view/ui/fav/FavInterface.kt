package com.example.weatherapp.view.ui.fav

import com.example.weatherapp.model.Welcome
import java.text.ParsePosition

interface FavInterface {
    fun deleteTask(welcome: Welcome,position: Int)
    fun selectTask(welcome: Welcome)
}