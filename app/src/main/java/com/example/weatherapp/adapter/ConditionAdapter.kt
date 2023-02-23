package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDesBinding
import com.example.weatherapp.model.Condition

class ConditionAdapter(
   var Conditions: List<Condition>
) : RecyclerView.Adapter<ConditionAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = Conditions[position]
        holder.binding.imgGrid.setImageResource(item.img)
        holder.binding.txtGridDes.text = item.des
        holder.binding.txtGridName.text =item.name
    }

    override fun getItemCount(): Int {
        return Conditions.size
    }

    inner class ViewHolder(
        val binding: ItemDesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}