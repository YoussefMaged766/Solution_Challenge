package com.example.solutionchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.solutionchallenge.adapter.intro_adapter

class IntroSliding_Activity : AppCompatActivity() {

    var adapter=intro_adapter (listOf(
        data_intro("Choose a customized diet for you", R.drawable.imgintro2),
        data_intro("Choose a customized exercises for you" , R.drawable.imgintro1)
    )
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_sliding)
        var viewPager :ViewPager2 = findViewById(R.id.viewpager_intro)
        viewPager.adapter=adapter
    }
}