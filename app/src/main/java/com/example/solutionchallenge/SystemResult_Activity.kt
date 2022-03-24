package com.example.solutionchallenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.ActivitySystemResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SystemResult_Activity : Fragment() {

    lateinit var sharedPref: SharedPreferences
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "kotlincod"
    lateinit var couponexpires: String
    lateinit var binding:ActivitySystemResultBinding
//    val data =
////        listOf("1/ teaspoon ginger paste",
////            "\n2/ teaspoon red chilli powder",
////            "\n3/ teaspoon cumin powder salt as required",
////            "\n4/ cup hung curd",
////            "\n5/ teaspoon garlic paste",
////            "\n6/ teaspoon coriander powder",
////            "\n7/ teaspoon powdered black pepper",
////            "\n8/ teaspoon garam masala powder",
////            "\n9/ 350 gm chicken")

    lateinit var data: meal_details

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_system_result, container, false)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        data = meal_details()


        var tall = arguments?.getInt("system_result", 0)

        if (tall != null) {
            if (tall > 50 && tall < 100) {

                var myObject: Nutration_adapter = Nutration_adapter(
                    arrayListOf(
                        Nutration_data(
                            "Side planks",
                            R.drawable.istockphoto2,
                            details_adapter(data.ex_data1),
                            "Steps",
                            0
                        ),
                        Nutration_data(
                            "Squats ",
                            R.drawable.istockphoto4,
                            details_adapter(data.ex_data2),
                            "Steps",
                            0
                        ),
                        Nutration_data(
                            "Push ups",
                            R.drawable.istockphoto7,
                            details_adapter(data.ex_data3),
                            "Steps",
                            0
                        )

                    )
                )

                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
                var gson: Gson = Gson()
                var json: String = gson.toJson(myObject)
                prefsEditor.putString("MyObject", json)
                prefsEditor.apply()
                binding.recyclerExercises.adapter = myObject
            } else if (tall > 110 && tall < 150) {

                var myObject: Nutration_adapter = Nutration_adapter(
                    arrayListOf(
                        Nutration_data(
                            "Grilled chiken",
                            R.drawable.istockphoto6,
                            details_adapter(data.data),
                            "1h 10min",
                            R.drawable.ic_baseline_access_time_24,
                            "Ingredients",
                            data.dir_data1,
                            "Directions"
                        ),
                        Nutration_data(
                            "Chicken & poultry",
                            R.drawable.istockphoto3,
                            details_adapter(data.data2),
                            "40min",
                            R.drawable.ic_baseline_access_time_24,
                            "Ingredients",
                            data.dir_data2,
                            "Directions"
                        ),
                        Nutration_data(
                            "High fat veggies",
                            R.drawable.istockphoto5,
                            details_adapter(data.data3),
                            "1h",
                            R.drawable.ic_baseline_access_time_24,
                            "Ingredients",
                            data.dir_data3,
                            "Directions"
                        )
                    )
                )

                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
                var gson: Gson = Gson()
                var json: String = gson.toJson(myObject)
                prefsEditor.putString("MyObject", json)
                prefsEditor.apply()
                binding.recyclerNutration.adapter = myObject
            }
        }
        binding.btnGetStarted.setOnClickListener {
            var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val date = Calendar.getInstance()
            var couponcreated = dateFormat.format(date.getTime())
            database.child("users").child(auth.uid.toString()).child("start_date")
                .setValue(couponcreated)

            date.add(Calendar.DATE, 91)

            couponexpires = dateFormat.format(date.getTime());
            var futureDate: Date = dateFormat.parse(couponexpires);
            database.child("users").child(auth.uid.toString()).child("end_date")
                .setValue(couponexpires)
            database.child("users").child(auth.uid.toString()).child("end_date_milli")
                .setValue(futureDate.time)

            it.findNavController().navigate(R.id.nav_Diet)


        }


        return binding.root

    }




}