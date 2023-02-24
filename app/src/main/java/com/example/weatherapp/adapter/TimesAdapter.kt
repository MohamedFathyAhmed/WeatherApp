package com.eram.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemTimesBinding

import com.example.weatherapp.model.*

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.hours

class TimesAdapter(private val context: Context,var hourly: List<Current>) : RecyclerView.Adapter<TimesAdapter.ViewHolder>() {
    lateinit var binding: ItemTimesBinding
    @RequiresApi(Build.VERSION_CODES.O)
    val hoursFromNow = (1..50).map { LocalTime.now().plusHours(it.toLong()).hour }
    @RequiresApi(Build.VERSION_CODES.O)
    val hours = hoursFromNow.map { if (it == 0) "12 AM" else if (it < 12) "$it AM" else if (it == 12) "12 PM" else "${it - 12} PM" }


    private val days = arrayListOf<String>()


    inner class ViewHolder(var binding: ItemTimesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemTimesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourly.size ?: 0
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item= hourly[position]
        holder.binding.hourWindSpeed.text = "${item.wind_speed} km/h"
        holder.binding.hourName.text = hours[position]
        holder.binding.hourTemp.text = "${item.temp}°"

        val iconUrl= "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        Glide.with(context).load(iconUrl)
            .apply(
                RequestOptions().override(400, 300).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
            ).into(  binding.hourWeatherIcon)

    }
    fun getWeatherIcon(state: WeatherState, currentTimeState: TimeState): Int {
        return WeatherStateMap.icon[Pair(
            state,
            currentTimeState
        )] ?: WeatherStateMap.icon[Pair(
            state,
            TimeState.Undefined
        )]!!
    }
}