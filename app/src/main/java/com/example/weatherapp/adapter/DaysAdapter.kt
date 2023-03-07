package com.eram.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.*

import com.example.weatherapp.databinding.ItemDaysBinding
import com.example.weatherapp.databinding.ItemTimesBinding
import com.example.weatherapp.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DaysAdapter(var daily: List<Daily>, val context: Context, private val itemClick: (weather: Weather) -> Unit) :
    RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    lateinit var binding: ItemDaysBinding
    val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val language =  sharedPreference.getString(CONST.lang,"en") !!


    inner class ViewHolder(var binding: ItemDaysBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemDaysBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return daily.size ?: 0
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = daily[position]



if (language.equals("en"))
        {
   holder.binding.dayTempMax.text = "${item.temp.max}${getCurrentTemperature(context)}"
            holder. binding.dayTempMin.text = "${item.temp.min}${getCurrentTemperature(context)}"
    }else{
    holder. binding.dayTempMax.text = "${convertStringToArabic(item.temp.max.toString())}${getCurrentTemperature(context)}"
    holder. binding.dayTempMin.text = "${convertStringToArabic(item.temp.min.toString())}${getCurrentTemperature(context)}"
       }
        holder. binding.dayName.text = convertToDay(item.dt, language)
        holder.  binding.dayState.text = item.weather[0].description
        holder. binding.dayWeatherIcon.setImageResource(getIconImage(item.weather[0].icon))


    }
}