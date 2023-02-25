package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.weatherapp.databinding.ItemFavBinding
import com.example.weatherapp.model.Condition
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.Welcome

class FavAdapter(
    var welcome: List<Welcome>, private val itemClick: (welcome: Welcome) -> Unit
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
        var item = welcome[position]
        holder.binding.deleteButton.setOnClickListener{
            itemClick(item)
        }
        holder.binding.localityText.text =item.timezone
    }

    override fun getItemCount(): Int {
        return welcome.size
    }

    inner class ViewHolder(
        val binding: ItemFavBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}