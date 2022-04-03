package com.example.solutionchallenge

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.ActivitySystemResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class SystemResult_fragment : Fragment() {

    lateinit var sharedPref: SharedPreferences
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "kotlincod"
    lateinit var couponexpires: String
    lateinit var binding: ActivitySystemResultBinding
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
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.activity_system_result, container, false)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        data = meal_details()

        conditions()




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

//        val callback = requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
//
//        }
//
//
//        callback.handleOnBackPressed()



        return binding.root

    }
    fun conditions(){
        var tall = arguments?.getInt("system_result", 0)
        var disease = arguments?.getString("spinner")
        Log.e("onCreateView: ", disease.toString() + tall.toString())

        if (disease == "Diabetes"){
            if (tall != null) {
                if (tall in 50..100) {

                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1), "Steps", 0,null,null,null,R.drawable.gif1),
                            Nutration_data("Push ups", R.drawable.istockphoto7, details_adapter(data.ex_data3), "Steps", 0,null,null,null,R.drawable.gif3),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )
                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                            Nutration_data("Mushroom pasta ", R.drawable.istockphoto14, details_adapter(data.data8), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data8, "Directions"),
                            Nutration_data("Vegan salad bowl", R.drawable.istockphoto15, details_adapter(data.data4), "Ingredients", R.drawable.ic_baseline_access_time_24, "20min", data.dir_data4, "Directions")
                        )
                    )

//                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(myObject)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
                    binding.recyclerExercises.adapter = myObject
                    binding.recyclerNutration.adapter = myObject1
                }
                else if (tall in 101..200) {

                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Sauteed chicken", R.drawable.istockphoto13, details_adapter(data.data9), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data9, "Directions"),
                            Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "40min", R.drawable.ic_baseline_access_time_24, "Ingredients", data.dir_data2, "Directions"),
                            Nutration_data("Cooked rice with\n" + "   vegetables", R.drawable.istockphoto16, details_adapter(data.data6), "Ingredients", R.drawable.ic_baseline_access_time_24, "50min", data.dir_data6, "Directions")
                        )
                    )
                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1), "Steps", 0,null,null,null,R.drawable.gif1),
                            Nutration_data("Push ups", R.drawable.istockphoto7, details_adapter(data.ex_data3), "Steps", 0,null,null,null,R.drawable.gif3),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )

                    binding.recyclerNutration.adapter = myObject1
                    binding.recyclerExercises.adapter = myObject


                }
                else{
                    Toast(requireContext() , " Invalid Tall")
                }
            }
        }
        else if (disease == "Hypertension"){
            if (tall != null) {
                if (tall in 50..100) {

                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Bicycle ", R.drawable.istockphoto9,details_adapter(data.ex_data5),"Steps",0,null,null,null,R.drawable.gif5),
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.data),"Steps",0,null,null,null,R.drawable.gif1),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )
                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                            Nutration_data("Orange fillet\n" + "with brocoli", R.drawable.istockphoto12, details_adapter(data.data7), "Ingredients", R.drawable.ic_baseline_access_time_24, "35min", data.dir_data7, "Directions"),
                            Nutration_data("Vegan salad bowl", R.drawable.istockphoto15, details_adapter(data.data4), "Ingredients", R.drawable.ic_baseline_access_time_24, "20min", data.dir_data4, "Directions")
                        )
                    )

//                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(myObject)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
                    binding.recyclerExercises.adapter = myObject
                    binding.recyclerNutration.adapter = myObject1
                }
                else if (tall in 101..200) {

                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("High fat veggies", R.drawable.istockphoto5, details_adapter(data.data3), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h", data.dir_data3, "Directions"),
                            Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "40min", R.drawable.ic_baseline_access_time_24, "Ingredients", data.dir_data2, "Directions"),
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions")
                        )
                    )
                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.data2),"Steps",0,null,null,null,R.drawable.gif2),
                            Nutration_data("Bicycle ", R.drawable.istockphoto9,details_adapter(data.ex_data5),"Steps",0,null,null,null,R.drawable.gif5),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )

                    binding.recyclerNutration.adapter = myObject1
                    binding.recyclerExercises.adapter = myObject


                }
                else{
                    Toast(requireContext() , " Invalid Tall")
                }
            }

        } else if (disease == "Renal failure"){
            if (tall != null) {
                if (tall in 50..100) {

                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1), "Steps", 0,null,null,null,R.drawable.gif1),
                            Nutration_data("Cardio", R.drawable.istockphoto8,details_adapter(data.ex_data4),"Steps",0,null,null,null,R.drawable.gif4),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )
                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                            Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "Ingredients", R.drawable.ic_baseline_access_time_24, "40min", data.dir_data2, "Directions"),
                            Nutration_data("Orange fillet\n" + "with brocoli", R.drawable.istockphoto12, details_adapter(data.data7), "Ingredients", R.drawable.ic_baseline_access_time_24, "35min", data.dir_data7, "Directions"),
                        )
                    )

//                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(myObject)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
                    binding.recyclerExercises.adapter = myObject
                    binding.recyclerNutration.adapter = myObject1
                }
                else if (tall in 101..200) {

                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("High fat veggies", R.drawable.istockphoto5, details_adapter(data.data3), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h", data.dir_data3, "Directions"),
                            Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "40min", R.drawable.ic_baseline_access_time_24, "Ingredients", data.dir_data2, "Directions"),
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                        )
                    )
                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1), "Steps", 0,null,null,null,R.drawable.gif1),
                            Nutration_data("Bicycle ", R.drawable.istockphoto9,details_adapter(data.ex_data5),"Steps",0,null,null,null,R.drawable.gif5),
                            Nutration_data("Dumble", R.drawable.istockphoto10,details_adapter(data.ex_data6),"Steps",0,null,null,null,R.drawable.gif6)
                        )
                    )

                    binding.recyclerNutration.adapter = myObject1
                    binding.recyclerExercises.adapter = myObject


                }
                else{
                    Toast(requireContext() , " Invalid Tall")
                }
            }

        }else if (disease == " Hepatic failure"){
            if (tall != null) {
                if (tall in 50..100) {

                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.ex_data1), "Steps", 0,null,null,null,R.drawable.gif1),
                            Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.data2),"Steps",0,null,null,null,R.drawable.gif2),
                            Nutration_data("Cardio", R.drawable.istockphoto8,details_adapter(data.ex_data4),"Steps",0,null,null,null,R.drawable.gif4)
                        )
                    )
                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Grilled chicken", R.drawable.istockphoto6, details_adapter(data.data), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data1, "Directions"),
                            Nutration_data("Sauteed chicken", R.drawable.istockphoto13, details_adapter(data.data9), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data9, "Directions"),
                            Nutration_data("Vegan salad bowl", R.drawable.istockphoto15, details_adapter(data.data4), "Ingredients", R.drawable.ic_baseline_access_time_24, "20min", data.dir_data4, "Directions")
                        )
                    )

//                var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(myObject)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
                    binding.recyclerExercises.adapter = myObject
                    binding.recyclerNutration.adapter = myObject1
                }
                else if (tall in 101..200) {

                    var myObject1: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Fresh spring rolls ", R.drawable.istockphoto11, details_adapter(data.data5), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data5, "Directions"),
                            Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "40min", R.drawable.ic_baseline_access_time_24, "Ingredients", data.dir_data2, "Directions"),
                            Nutration_data("Cooked rice with\n" + "   vegetables", R.drawable.istockphoto16, details_adapter(data.data6), "Ingredients", R.drawable.ic_baseline_access_time_24, "50min", data.dir_data6, "Directions")
                        )
                    )
                    var myObject: Nutration_adapter = Nutration_adapter(
                        arrayListOf(
                            Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.data2),"Steps",0,null,null,null,R.drawable.gif2),
                            Nutration_data("Push ups", R.drawable.istockphoto7, details_adapter(data.ex_data3), "Steps", 0,null,null,null,R.drawable.gif3),
                            Nutration_data("Bicycle ", R.drawable.istockphoto9,details_adapter(data.ex_data5),"Steps",0,null,null,null,R.drawable.gif5)
                        )
                    )

                    binding.recyclerNutration.adapter = myObject1
                    binding.recyclerExercises.adapter = myObject


                }
                else{
                    Toast(requireContext() , " Invalid Tall")
                }
            }
        }
        else{
            var myObject1: Nutration_adapter = Nutration_adapter(
                arrayListOf(
                    Nutration_data("Grilled chicken", R.drawable.istockphoto6, details_adapter(data.data), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h 10min", data.dir_data1, "Directions"),
                    Nutration_data("Chicken & poultry", R.drawable.istockphoto3, details_adapter(data.data2), "Ingredients", R.drawable.ic_baseline_access_time_24, "40min", data.dir_data2, "Directions"),
                    Nutration_data("High fat veggies", R.drawable.istockphoto5, details_adapter(data.data3), "Ingredients", R.drawable.ic_baseline_access_time_24, "1h", data.dir_data3, "Directions")
                )
            )
            var myObject: Nutration_adapter = Nutration_adapter(
                arrayListOf(
                    Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data.data),"Steps",0,null,null,null,R.drawable.gif1),
                    Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data.data2),"Steps",0,null,null,null,R.drawable.gif2),
                    Nutration_data("Push ups", R.drawable.istockphoto7,details_adapter(data.ex_data3),"Steps",0,null,null,null,R.drawable.gif3)
                )
            )

            binding.recyclerNutration.adapter = myObject1
            binding.recyclerExercises.adapter = myObject
        }
    }




}