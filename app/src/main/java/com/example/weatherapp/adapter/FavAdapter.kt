package com.example.weatherapp.adapter

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
    var welcome: MutableList<Welcome>, private val favInterface: FavInterface
) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

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
        var time = convertToTime(item.current.dt,language)
      var date = convertToDate(item.current.dt,language)
        holder.binding.localityText.text ="${item.timezone} : ${date} ${time}"
    }

    override fun getItemCount(): Int {
        return welcome.size
    }

    inner class ViewHolder(
        val binding: ItemFavBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}