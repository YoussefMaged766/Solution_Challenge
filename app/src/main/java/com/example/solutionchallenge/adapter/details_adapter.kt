package com.example.solutionchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.classes.Nutration_data



class details_adapter(var items: List<String>):
    RecyclerView.Adapter<details_adapter.viewholder>() {


    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var txt = itemView.findViewById<TextView>(R.id.txt_details)

        fun bind(introslide: String) {
            txt.text = introslide



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.meal_details_item,parent,false)
        return viewholder(v)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

}