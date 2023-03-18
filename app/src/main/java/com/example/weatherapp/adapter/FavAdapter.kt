package com.example.weatherapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.CONST
import com.example.weatherapp.convertToDate
import com.example.weatherapp.convertToDay
import com.example.weatherapp.convertToTime

import com.example.weatherapp.databinding.ItemFavBinding
import com.example.weatherapp.model.Condition
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.Welcome
import com.example.weatherapp.view.ui.fav.FavInterface

class FavAdapter(var context: Context,
    var welcome: List<Welcome>, private val favInterface: FavInterface
) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreference =  context.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        val language =  sharedPreference.getString(CONST.lang,"en") !!

        var item = welcome[position]
        holder.binding.deleteButton.setOnClickListener{
            favInterface.deleteTask(item,position)
        }

        holder.binding.constraintLayout.setOnClickListener{
            favInterface.selectTask(item)
        }
        var time =item.current?.let { convertToTime(it.dt,language) }
      var date = item.current?.let { convertToDate(it.dt,language) }
        if(language=="en"){
            holder.binding.localityText.text ="${item.timezone}"
            holder.binding.timeText.text =" ${date} : ${time}"
        }else{
            holder.binding.localityText.text ="${item.timezonear} "
            holder.binding.timeText.text =" ${date} : ${time}"
        }

    }

    override fun getItemCount(): Int {
        return welcome.size
    }

    inner class ViewHolder(
        val binding: ItemFavBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}