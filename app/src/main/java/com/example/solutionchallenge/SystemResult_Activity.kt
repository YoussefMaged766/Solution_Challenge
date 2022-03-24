package com.example.solutionchallenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data
import com.example.solutionchallenge.classes.meal_details
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SystemResult_Activity : AppCompatActivity() {
    lateinit var recycler_Nutration: RecyclerView
    lateinit var recyclerExercises: RecyclerView
    lateinit var adapter_Nutration: Nutration_adapter
    lateinit var adapter_Exercises: Nutration_adapter
    lateinit var btn_start: Button
    lateinit var sharedPref: SharedPreferences
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userArrayList: ArrayList<Nutration_data>
    private val PREFS_NAME = "kotlincod"
    lateinit var couponexpires: String
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_result)
        recycler_Nutration = findViewById(R.id.recycler_Nutration)
        recyclerExercises = findViewById(R.id.recycler_Exercises)
        btn_start = findViewById(R.id.btn_getStarted)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        userArrayList = arrayListOf<Nutration_data>()
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        data = meal_details()
        adapter_Nutration = Nutration_adapter(
            arrayListOf(
                Nutration_data(
                    "Grilled chiken",
                    R.drawable.istockphoto6,
                    details_adapter(data.data),
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24,
                    "1h 10min",
                    data.dir_data1,
                    "Directions"
                ),
                Nutration_data(
                    "Chicken & poultry",
                    R.drawable.istockphoto3,
                    details_adapter(data.data2),
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24,
                    "40min",
                    data.dir_data2,
                    "Directions"
                ),
                Nutration_data(
                    "High fat veggies",
                    R.drawable.istockphoto5,
                    details_adapter(data.data3),
                    "Ingredients",
                    R.drawable.ic_baseline_access_time_24,
                    "1h",
                    data.dir_data3,
                    "Directions"
                )

            )
        )

        adapter_Exercises = Nutration_adapter(
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

//        adapter_Exercises = Nutration_adapter(
//            arrayListOf(
//                getIntent().getSerializableExtra("adapter"),
//                getIntent().getSerializableExtra("adapter1"),
//                getIntent().getSerializableExtra("adapter2")
//
//            ) as ArrayList<Nutration_data>
//        )

        var tall = intent.getIntExtra("tall", 0)

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
//            recyclerExercises.adapter = adapter_Exercises
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
                        "",
                        "Directions"
                    ),
                    Nutration_data(
                        "Chicken & poultry",
                        R.drawable.istockphoto3,
                        details_adapter(data.data2),
                        "40min",
                        R.drawable.ic_baseline_access_time_24,
                        "Ingredients",
                        "",
                        "Directions"
                    ),
                    Nutration_data(
                        "High fat veggies",
                        R.drawable.istockphoto5,
                        details_adapter(data.data3),
                        "1h",
                        R.drawable.ic_baseline_access_time_24,
                        "Ingredients",
                        "",
                        "Directions"
                    )
                )
            )

            var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
            var gson: Gson = Gson()
            var json: String = gson.toJson(myObject)
            prefsEditor.putString("MyObject", json)
            prefsEditor.apply()
//            recyclerExercises.adapter = adapter_Exercises
        }

        var gson: Gson = Gson()
        val json1: String = sharedPref.getString("MyObject", "").toString()
        val obj: Nutration_adapter = gson.fromJson(json1, Nutration_adapter::class.java)
        recycler_Nutration.adapter = obj
        Log.e("onCreate: ", json1)


//        recycler_Nutration.adapter = adapter_Nutration
//        recycler_Nutration.adapter = adapter_Nutration

        btn_start.setOnClickListener {
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

            startActivity(Intent(this, TimerActivity::class.java))
            finish()


        }
    }


}