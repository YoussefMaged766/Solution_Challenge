package com.example.solutionchallenge.ui.Exersies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.FragmentExersieseBinding

class ExersiesFragment : Fragment() {

    lateinit var binding: FragmentExersieseBinding
    lateinit var adapter_home: Nutration_adapter
    lateinit var adapter_gym: Nutration_adapter


    lateinit var data :meal_details

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exersiese, container, false)
data = meal_details()
        adapter_home = Nutration_adapter(
            arrayListOf(
                Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1),"Steps",0),
                Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.ex_data2),"Steps",0),
                Nutration_data("Push ups", R.drawable.istockphoto7,details_adapter(data.ex_data3),"Steps",0)
            )
        )

        adapter_gym = Nutration_adapter(arrayListOf(
            Nutration_data("Cardio", R.drawable.istockphoto8,details_adapter(data.ex_data4),"Steps",0),
            Nutration_data("Bicycle ", R.drawable.istockphoto9,details_adapter(data.ex_data5),"Steps",0),
            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0)
        ))

        binding.recyclerHomeExersies.adapter = adapter_home
        binding.recyclerExercisesGym.adapter = adapter_gym





        return binding.root
    }


}