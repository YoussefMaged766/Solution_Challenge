package com.example.solutionchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register_Activity : AppCompatActivity() {

    lateinit var create_btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        create_btn = findViewById(R.id.btn_createAccount)
        create_btn.setOnClickListener {
            startActivity(Intent(this , UserProfile_Activity::class.java))
        }
    }
}