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
                Nutration_data("Grilled chicken", R.drawable.istockphoto6, details_adapter(data.data), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data1, "Directions"),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "Ingredients", R.drawable.ic_baseline_access_time_24, "40min", data.dir_data2, "Directions"),
                Nutration_data("High fat veggies", R.drawable.istockphoto5, details_adapter(data.data3), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h", data.dir_data3, "Directions")

            )
        )

        adapter_vegan = Nutration_adapter(
            arrayListOf(
                Nutration_data("Vegan salad bowl", R.drawable.istockphoto15, details_adapter(data.data4), "Ingredients", R.drawable.ic_baseline_access_time_24, "20min", data.dir_data4, "Directions"),
                Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                Nutration_data("Cooked rice with\n" + "   vegetables", R.drawable.istockphoto16, details_adapter(data.data6), "Ingredients", R.drawable.ic_baseline_access_time_24, "50min", data.dir_data6, "Directions")
            )
        )
        adapter_Atkins = Nutration_adapter(
            arrayListOf(
                Nutration_data("Orange fillet\n" + "with brocoli", R.drawable.istockphoto12, details_adapter(data.data7), "Ingredients", R.drawable.ic_baseline_access_time_24, "35min", data.dir_data7, "Directions"),
                Nutration_data("Mushroom pasta ", R.drawable.istockphoto14, details_adapter(data.data8), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data8, "Directions"),
                Nutration_data("Sauteed chicken", R.drawable.istockphoto13, details_adapter(data.data9), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data9, "Directions")
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