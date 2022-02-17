package com.example.solutionchallenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.solutionchallenge.adapter.intro_adapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IntroSliding_Activity : AppCompatActivity() {

    lateinit var viewPager:ViewPager2
    lateinit var  indicator_container :LinearLayout
    lateinit var btn_right :FloatingActionButton
    lateinit var btn_left :FloatingActionButton
    lateinit var prefrence :SharedPreferences
    var pref_show_intro = "Intro"

    var adapter=intro_adapter (listOf(
        data_intro("Choose a customized diet for you", R.drawable.imgintro2),
        data_intro("Choose a customized exercises for you" , R.drawable.imgintro1)
    )
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_sliding)
        initallize()
        indicators()
        setCurrentIndicators(0)
        viewPager.adapter=adapter
        prefrence = getSharedPreferences("IntroSlider" , Context.MODE_PRIVATE)
        if (!prefrence.getBoolean(pref_show_intro,true)){
            startActivity( Intent(applicationContext, Register_Activity:: class.java))
            finish()
        }
        viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicators(position)
            }
        })
        btn_right.setOnClickListener {
            if (viewPager.currentItem + 1 < adapter.itemCount) {
                viewPager.currentItem +=1
            }else{
                Intent(applicationContext, Register_Activity:: class.java).also {
                    startActivity(it)
                    val editor = prefrence.edit()
                    editor.putBoolean(pref_show_intro,false)
                    editor.apply()
                }
            }
        }
        btn_left.setOnClickListener {
                viewPager.currentItem -=1
        }

    }

    fun initallize(){
         viewPager  = findViewById(R.id.viewpager_intro)
        indicator_container = findViewById(R.id.indicator_container)
        btn_right=findViewById(R.id.floating_right_btn)
        btn_left=findViewById(R.id.floating_left_btn)
    }

    fun indicators(){

        val indicator = arrayOfNulls<ImageView>(adapter.itemCount)
        val  layoutparams:LinearLayout.LayoutParams=
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        layoutparams.setMargins(15,0,10,0)
        for (i in indicator.indices){
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,R.drawable.inactive
                    )
                )
                this?.layoutParams = layoutparams
            }
                indicator_container.addView(indicator[i])
            }
        }
     fun setCurrentIndicators(index : Int) {
        val childCount = indicator_container.childCount
        for (i in 0 until childCount) {
            val imageView =
                indicator_container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.inactive
                    )
                )
            }
        }
    }
    }

