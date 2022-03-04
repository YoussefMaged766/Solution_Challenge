package com.example.solutionchallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.solutionchallenge.R
import com.example.solutionchallenge.SystemInfo_Activity
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.databinding.FragmentHomeBinding
import com.example.solutionchallenge.ui.Exersies.ExersiesFragment
import com.example.solutionchallenge.ui.meal.MealFragment

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    lateinit var adapter_Nutration: Nutration_adapter
    lateinit var adapter_Exercises: Nutration_adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



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
        SnapHelper.attachToRecyclerView(binding.recyclerMeals)
        SnapHelper.attachToRecyclerView(binding.recyclerExercises)


        binding.recyclerMeals.adapter = adapter_Nutration
        binding.recyclerExercises.adapter = adapter_Exercises



        binding.btnPickPlan.setOnClickListener {
            startActivity( Intent(context, SystemInfo_Activity::class.java))
        }

        binding.btnAddMeals.setOnClickListener {

            binding.recyclerMeals.smoothScrollToPosition(adapter_Nutration.itemCount - 1)

        }
        binding.txtSeemoreMeals.setOnClickListener {

            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.nav_meal)

        }
        binding.txtSeemoreExersise.setOnClickListener {
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.nav_Exercise)
        }

















        return binding.root
    }

}