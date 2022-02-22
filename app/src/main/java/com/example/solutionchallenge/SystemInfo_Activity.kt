package com.example.solutionchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class SystemInfo_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_info)
        val spinner :Spinner = findViewById(R.id.spinner_disease)
        val diseases = resources.getStringArray(R.array.spinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, diseases
        )
        adapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }
}