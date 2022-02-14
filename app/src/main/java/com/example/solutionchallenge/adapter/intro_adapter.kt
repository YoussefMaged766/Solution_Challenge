package com.example.solutionchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.data_intro

class intro_adapter(var items:List<data_intro>): RecyclerView.Adapter<intro_adapter.viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.sliding_item,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

            var img = itemView.findViewById<ImageView>(R.id.image)
            var txtdis = itemView.findViewById<TextView>(R.id.txt_description_intro)

            fun bind (introslide:data_intro){
                txtdis.text =introslide.txt_dis
                img.setImageResource(introslide.img)

            }
    }


}