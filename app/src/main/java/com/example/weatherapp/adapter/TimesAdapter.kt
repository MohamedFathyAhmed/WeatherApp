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
import com.example.weatherapp.*
import com.example.weatherapp.databinding.ItemTimesBinding

import com.example.weatherapp.model.*

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.hours

class TimesAdapter(private val context: Context,var hourly: List<Current>) : RecyclerView.Adapter<TimesAdapter.ViewHolder>() {
    lateinit var binding: ItemTimesBinding
    val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val language =  sharedPreference.getString(CONST.lang,"en") !!

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
        if (language.equals("en")) {

            holder.binding.hourWindSpeed.text = "${item.wind_speed} km/h"

            holder.binding.hourTemp.text = "${item.temp}°"

        }else{
            holder.binding.hourWindSpeed.text = "${convertStringToArabic(item.wind_speed.toString())} كم/س"

            holder.binding.hourTemp.text = "${convertStringToArabic(item.temp.toString())}°"


        }
        holder.binding.hourName.text = convertToTime(item.dt, language)
        binding.hourWeatherIcon.setImageResource(getIconImage(item.weather[0].icon))

    }

}