package com.eram.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.*
import com.example.weatherapp.databinding.ItemAlertBinding

import com.example.weatherapp.model.*
import java.util.*

class AlertAdapter(var myAlerts: List<MyAlert>, val context: Context, private val deleteAlertAction: (myAlert: MyAlert) -> Unit) :
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
        return myAlerts.size ?: 0
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = myAlerts[position]

        //    holder.binding.txtTo.text ="${convertToDate( item.endDay,language)}:${convertToTime(item.Time,language)}"
        holder.binding.txtFrom.text = convertToDate( item.startDay,language)
        holder.binding.txtTime.text =convertToTime(item.Time,language)
        holder.binding.txtTo.text =convertToDate( item.endDay,language)
        holder.binding.imgDelete.setOnClickListener {
            deleteAlertAction(item)
        }

    }
}