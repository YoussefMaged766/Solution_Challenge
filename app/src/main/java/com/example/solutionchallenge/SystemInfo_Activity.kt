package com.example.solutionchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.classes.Nutration_data

class SystemInfo_Activity : AppCompatActivity() {
lateinit var btn_submit :Button
lateinit var spinner: Spinner
lateinit var  adapter1: Nutration_adapter
lateinit var txt_tall :EditText
lateinit var btn_result : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_info)
         initialize()

        val diseases = resources.getStringArray(R.array.spinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, diseases
        )
        adapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item)
        spinner.adapter = adapter

        adapter1 = Nutration_adapter(
            arrayListOf(
                Nutration_data("Side planks", R.drawable.istockphoto2),
                Nutration_data("Squats ", R.drawable.istockphoto4),
                Nutration_data("Push ups", R.drawable.istockphoto7)

            )
        )

        btn_submit.setOnClickListener {


            var i =Intent(this , SystemResult_Activity::class.java)
            var tall = Integer.parseInt(txt_tall.text.toString())
            i.putExtra("tall" ,tall )

            startActivity(i)

        }
        btn_result.setOnClickListener {


            var i =Intent(this , SystemResult_Activity::class.java)
            startActivity(i)

        }

    }
    fun initialize(){
        spinner  = findViewById(R.id.spinner_disease)
        btn_submit = findViewById(R.id.btn_submit_system)
        txt_tall = findViewById(R.id.tall_edit_system)
        btn_result = findViewById(R.id.btn_submit_system_result)

    }
}