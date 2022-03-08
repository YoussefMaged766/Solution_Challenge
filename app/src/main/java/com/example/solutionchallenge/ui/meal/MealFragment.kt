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
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.databinding.FragmentMealBinding

class MealFragment : Fragment() {

    lateinit var binding: FragmentMealBinding
    lateinit var adapter_keto: Nutration_adapter
    lateinit var adapter_vegan: Nutration_adapter
    lateinit var adapter_Atkins: Nutration_adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal, container, false)

        adapter_keto = Nutration_adapter(
            arrayListOf(
                Nutration_data("Grilled chiken", R.drawable.istockphoto6),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3),
                Nutration_data("High fat veggies", R.drawable.istockphoto5)

            )
        )


        adapter_vegan = Nutration_adapter(
            arrayListOf(
                Nutration_data("Vegan salad bowl", R.drawable.istockphoto15),
                Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11),
                Nutration_data("Cooked rice with\n" + "   vegetables", R.drawable.istockphoto16)

            )
        )
        adapter_Atkins = Nutration_adapter(
            arrayListOf(
                Nutration_data("Orange fillet\n" + "with brocoli", R.drawable.istockphoto12),
                Nutration_data("Mushroom pasta ", R.drawable.istockphoto14),
                Nutration_data("Sauteed chicken", R.drawable.istockphoto13)

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