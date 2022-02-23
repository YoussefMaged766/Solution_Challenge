package com.example.solutionchallenge


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class HomeActivity : AppCompatActivity() {
    lateinit var btn_systemInfo :Button
    lateinit var btn_userInfo :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_systemInfo = findViewById(R.id.btn_systeminfo)
        btn_userInfo = findViewById(R.id.btn_userprofile)


        btn_userInfo.setOnClickListener {
           intent( UserProfile_Activity::class.java)
        }

        btn_systemInfo.setOnClickListener {
            intent( SystemInfo_Activity::class.java)
        }

    }
    fun intent( activity: Class<*>){
        startActivity(Intent(applicationContext , activity))
    }

}