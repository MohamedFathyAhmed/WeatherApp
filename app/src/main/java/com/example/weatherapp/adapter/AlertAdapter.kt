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
import com.example.weatherapp.databinding.ItemAlertBinding

import com.example.weatherapp.databinding.ItemDaysBinding
import com.example.weatherapp.databinding.ItemTimesBinding
import com.example.weatherapp.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlertAdapter(var alerts: List<Alert>, val context: Context, private val deleteAlertAction: (alert: Alert) -> Unit) :
    RecyclerView.Adapter<AlertAdapter.ViewHolder>() {
    lateinit var binding: ItemAlertBinding
    val sharedPreference = context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    val language =  sharedPreference.getString(CONST.lang,"en") !!


    inner class ViewHolder(var binding: ItemAlertBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemAlertBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alerts.size ?: 0
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alerts[position]

if (language.equals("en"))
        {
            holder.binding.txtFrom.text = item.startDay.toString()
            holder.binding.txtTo.text = item.endDay.toString()

    }else{
// TODO: when select arabic


       }
        holder.binding.imgDelete.setOnClickListener {
            deleteAlertAction(item)
        }

    }
}