package com.example.solutionchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.classes.Nutration_data

class SystemResult_Activity : AppCompatActivity() {
    lateinit var recycler_Nutration:RecyclerView
    lateinit var recyclerExercises:RecyclerView
    lateinit var adapter_Nutration :Nutration_adapter
    lateinit var adapter_Exercises :Nutration_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_result)
        recycler_Nutration = findViewById(R.id.recycler_Nutration)
        recyclerExercises = findViewById(R.id.recycler_Exercises)
        adapter_Nutration = Nutration_adapter(
            listOf(
            Nutration_data("Grilled chiken",R.drawable.istockphoto6),
                Nutration_data("Chicken & poultry",R.drawable.istockphoto3),
                Nutration_data("High fat veggies",R.drawable.istockphoto5)

            )
        )


        adapter_Exercises = Nutration_adapter(
            listOf(
                Nutration_data("Side planks",R.drawable.istockphoto2),
                Nutration_data("Squats ",R.drawable.istockphoto4),
                Nutration_data("Push ups",R.drawable.istockphoto7)

            )
        )

        recycler_Nutration.adapter = adapter_Nutration
        recyclerExercises.adapter = adapter_Exercises
    }
}