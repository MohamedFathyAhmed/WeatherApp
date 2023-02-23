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

import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemDaysBinding
import com.example.weatherapp.databinding.ItemTimesBinding
import com.example.weatherapp.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DaysAdapter(var daily: List<Daily>, val context: Context, private val itemClick: (weather: Weather) -> Unit) :
    RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    lateinit var binding: ItemDaysBinding
    private val days = arrayListOf<String>()


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
     //  holder.binding.txtDay.text = days[position]
        holder.binding.txtDegreeRange.text = "${item.temp.min}°/${item.temp.max}°"
        val iconUrl= "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        Glide.with(context).load(iconUrl)
            .apply(
                RequestOptions().override(400, 300).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
            ).into(  binding.imgIcon)

    }
}