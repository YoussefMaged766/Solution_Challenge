package com.example.solutionchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class SystemInfo_Activity : AppCompatActivity() {
lateinit var btn_submit :Button
lateinit var spinner: Spinner

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

        btn_submit.setOnClickListener {
            startActivity(Intent(this , SystemResult_Activity::class.java))
        }

    }
    fun initialize(){
        spinner  = findViewById(R.id.spinner_disease)
        btn_submit = findViewById(R.id.btn_submit_system)

    }
}