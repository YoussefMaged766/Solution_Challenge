package com.example.solutionchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.data_intro
import com.example.solutionchallenge.databinding.FragmentExersieseBinding

class Nutration_adapter(var items:ArrayList<Nutration_data>):RecyclerView.Adapter<Nutration_adapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var img = itemView.findViewById<ImageView>(R.id.imageView_list)
        var txt = itemView.findViewById<TextView>(R.id.txtname_list)

        fun bind (introslide: Nutration_data){
            txt.text =introslide.name
            img.setImageResource(introslide.img)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutration_item_list,parent,false)
        return viewholder(view)

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])





    }

    override fun getItemCount(): Int {
        return items.size
    }


}