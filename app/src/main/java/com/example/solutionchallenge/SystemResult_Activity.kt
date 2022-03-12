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
import com.example.solutionchallenge.classes.Nutration_data
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
    lateinit var couponexpires:String
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
        adapter_Nutration = Nutration_adapter(
            arrayListOf(
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

            var myObject: Nutration_adapter =  Nutration_adapter(
                arrayListOf(
                    Nutration_data("Side planks", R.drawable.istockphoto3),
                    Nutration_data("Squats ", R.drawable.istockphoto4),
                    Nutration_data("Push ups", R.drawable.istockphoto7)

                )
            )

            var prefsEditor: SharedPreferences.Editor = sharedPref.edit()
            var gson: Gson = Gson()
            var json: String = gson.toJson(myObject)
            prefsEditor.putString("MyObject", json)
            prefsEditor.apply()
//            recyclerExercises.adapter = adapter_Exercises
        }
        else if (tall > 110 && tall < 150) {

            var myObject: Nutration_adapter =  Nutration_adapter(
                arrayListOf(
                    Nutration_data("Grilled chiken", R.drawable.istockphoto6),
                    Nutration_data("Chicken & poultry", R.drawable.istockphoto3),
                    Nutration_data("High fat veggies", R.drawable.istockphoto5)

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
        Log.e( "onCreate: ",json1 )


//        recycler_Nutration.adapter = adapter_Nutration
//        recycler_Nutration.adapter = adapter_Nutration

        btn_start.setOnClickListener {
            var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val date = Calendar.getInstance()
            var couponcreated = dateFormat.format(date.getTime())
            database.child("users").child(auth.uid.toString()).child("start_date").setValue(couponcreated)

            date.add(Calendar.DATE,91)

            couponexpires = dateFormat.format(date.getTime());
            var futureDate: Date = dateFormat.parse(couponexpires);
            database.child("users").child(auth.uid.toString()).child("end_date").setValue(couponexpires)
            database.child("users").child(auth.uid.toString()).child("end_date_milli").setValue(futureDate.time)

            startActivity(Intent(this , TimerActivity::class.java))
            finish()


        }
    }


}