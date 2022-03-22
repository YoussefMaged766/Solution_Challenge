package com.example.solutionchallenge.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.solutionchallenge.R
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.FragmentMealBinding

class MealFragment : Fragment() {

    lateinit var binding: FragmentMealBinding
    lateinit var adapter_keto: Nutration_adapter
    lateinit var adapter_vegan: Nutration_adapter
    lateinit var adapter_Atkins: Nutration_adapter

    lateinit var data: meal_details

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal, container, false)
        data = meal_details()
        adapter_keto = Nutration_adapter(
            arrayListOf(
                Nutration_data(
                    "Grilled chicken",
                    R.drawable.istockphoto6,
                    details_adapter(data.data),
                    "1h 10min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "Chicken & poultry",
                    R.drawable.istockphoto3,
                    details_adapter(data.data2),
                    "40min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "High fat veggies",
                    R.drawable.istockphoto5,
                    details_adapter(data.data3),
                    "1h",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                )

            )
        )

        adapter_vegan = Nutration_adapter(
            arrayListOf(
                Nutration_data(
                    "Vegan salad bowl",
                    R.drawable.istockphoto15,
                    details_adapter(data.data4),
                    "20min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "Fresh spring rolls ",
                    R.drawable.istockphoto11,
                    details_adapter(data.data5),
                    "1h 10min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "Cooked rice with\n" + "   vegetables",
                    R.drawable.istockphoto16,
                    details_adapter(data.data6),
                    "50min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                )

            )
        )
        adapter_Atkins = Nutration_adapter(
            arrayListOf(
                Nutration_data(
                    "Orange fillet\n" + "with brocoli",
                    R.drawable.istockphoto12,
                    details_adapter(data.data7),
                    "35min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "Mushroom pasta ",
                    R.drawable.istockphoto14,
                    details_adapter(data.data8),
                    "1h 10min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                ),
                Nutration_data(
                    "Sauteed chicken",
                    R.drawable.istockphoto13,
                    details_adapter(data.data9),
                    "1h 10min",
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24
                )

            )
        )

        val SnapHelper = PagerSnapHelper()
        SnapHelper.attachToRecyclerView(binding.recyclerKetodiet)
        SnapHelper.attachToRecyclerView(binding.recyclerVeganDiet)
        SnapHelper.attachToRecyclerView(binding.recyclerAtkinsDiet)


        binding.recyclerKetodiet.adapter = adapter_keto
        binding.recyclerVeganDiet.adapter = adapter_vegan
        binding.recyclerAtkinsDiet.adapter = adapter_Atkins




        return binding.root
    }


}