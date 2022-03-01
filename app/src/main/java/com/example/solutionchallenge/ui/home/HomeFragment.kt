package com.example.solutionchallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.SystemInfo_Activity
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    lateinit var recycler_Nutration: RecyclerView
    lateinit var recyclerExercises: RecyclerView
    lateinit var adapter_Nutration: Nutration_adapter
    lateinit var adapter_Exercises: Nutration_adapter
    lateinit var rightbuttom_meals: FloatingActionButton
    lateinit var btn_pick_plan: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_home, container, false)
        recycler_Nutration = v.findViewById(R.id.recycler_meals)
        recyclerExercises = v.findViewById(R.id.recycler_Exercises)
        btn_pick_plan = v.findViewById(R.id.btn_pick_plan)
        rightbuttom_meals = v.findViewById(R.id.btn_add_meals)



        adapter_Nutration = Nutration_adapter(
            arrayListOf(
                Nutration_data("Grilled chiken", R.drawable.istockphoto6),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3),
                Nutration_data("High fat veggies", R.drawable.istockphoto5),
                Nutration_data("Grilled chiken", R.drawable.istockphoto6),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3),
                Nutration_data("High fat veggies", R.drawable.istockphoto5)

            )
        )


        adapter_Exercises = Nutration_adapter(
            arrayListOf(
                Nutration_data("Side planks", R.drawable.istockphoto2),
                Nutration_data("Squats ", R.drawable.istockphoto4),
                Nutration_data("Push ups", R.drawable.istockphoto7)

            )
        )

        val SnapHelper = PagerSnapHelper()
        SnapHelper.attachToRecyclerView(recycler_Nutration)

        recycler_Nutration.adapter = adapter_Nutration
        recyclerExercises.adapter = adapter_Exercises



        btn_pick_plan.setOnClickListener {


           var i =Intent(context, SystemInfo_Activity::class.java)

            startActivity(i)
        }

        rightbuttom_meals.setOnClickListener {

                    recycler_Nutration.smoothScrollToPosition(adapter_Nutration.itemCount - 1)

        }

















        return v
    }

}