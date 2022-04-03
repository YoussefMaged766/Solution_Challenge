package com.example.solutionchallenge.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.classes.Nutration_data


class Nutration_adapter(var items: ArrayList<Nutration_data>) :
    RecyclerView.Adapter<Nutration_adapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById<ImageView>(R.id.imageView_list)
        var txt = itemView.findViewById<TextView>(R.id.txtname_list)

        fun bind(introslide: Nutration_data) {
            txt.text = introslide.name
            img.setImageResource(introslide.img)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.nutration_item_list, parent, false)
        return viewholder(view)

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {

            send_data(items[position], it)


        }


    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun send_data(data: Nutration_data, view: View) {
        var bundle: Bundle = Bundle()

        bundle.putSerializable("item_sheet", data)
        view.findNavController().navigate(R.id.nav_sheet, bundle)
    }


}