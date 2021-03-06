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
import com.example.solutionchallenge.Register_Activity

import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    lateinit var adapter_Nutration: Nutration_adapter
    lateinit var adapter_Exercises: Nutration_adapter
    private lateinit var database: DatabaseReference
    lateinit var data: meal_details
//    val data =
//        listOf("teaspoon ginger paste",
//            " teaspoon red chilli powder",
//            " teaspoon cumin powder salt as required",
//            " cup hung curd",
//            " teaspoon garlic paste",
//            " teaspoon coriander powder",
//            " teaspoon powdered black pepper",
//            " teaspoon garam masala powder",
//            " 350 gm chicken")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        data = meal_details()


        adapter_Nutration = Nutration_adapter(
            arrayListOf(
                Nutration_data("Grilled chiken", R.drawable.istockphoto6, details_adapter(data.data),"Ingredients",R.drawable.ic_baseline_access_time_24,"1h 10min" ,data.dir_data1,"Directions"),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3,details_adapter(data.data2),"Ingredients",R.drawable.ic_baseline_access_time_24,"40min" ,  data.dir_data2,"Directions"),
                Nutration_data("High fat veggies", R.drawable.istockphoto5,details_adapter(data.data3),"Ingredients",R.drawable.ic_baseline_access_time_24,"1h" ,  data.dir_data3,"Directions"),
                Nutration_data("Grilled chiken", R.drawable.istockphoto6,details_adapter(data.data),"Ingredients",R.drawable.ic_baseline_access_time_24,"1h 10min"  , data.dir_data1,"Directions"),
                Nutration_data("Chicken & poultry", R.drawable.istockphoto3,details_adapter(data.data2),"Ingredients", R.drawable.ic_baseline_access_time_24,"40min" , data.dir_data2,"Directions"),
                Nutration_data("High fat veggies", R.drawable.istockphoto5,details_adapter(data.data3),"Ingredients",R.drawable.ic_baseline_access_time_24,"1h " ,  data.dir_data3,"Directions")

            )
        )


        adapter_Exercises = Nutration_adapter(
            arrayListOf(
                Nutration_data("Side planks", R.drawable.istockphoto2,details_adapter(data.ex_data1),"Steps",0,null,null,null,R.drawable.gif1),
                Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.ex_data2),"Steps",0,null,null,null,R.drawable.gif2),
                Nutration_data("Push ups", R.drawable.istockphoto7,details_adapter(data.ex_data3),"Steps",0,null,null,null,R.drawable.gif3)

            )
        )

        val SnapHelper = PagerSnapHelper()
        SnapHelper.attachToRecyclerView(binding.recyclerMeals)
        SnapHelper.attachToRecyclerView(binding.recyclerExercises)


        binding.recyclerMeals.adapter = adapter_Nutration
        binding.recyclerExercises.adapter = adapter_Exercises



        binding.btnPickPlan.setOnClickListener {
            database.child("users").child(auth.uid.toString()).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
                        val navController = navHostFragment.navController
                        navController.navigate(R.id.systemInfo_Activity)
                    }
                    else{
                        Snackbar.make(binding.containerHome, "you should sign in first", Snackbar.LENGTH_LONG)
                            .setAction("Sign up") {
                                startActivity(Intent(requireContext() , Register_Activity::class.java) )

                            }
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}