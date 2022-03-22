package com.example.solutionchallenge.classes

import android.graphics.drawable.Drawable
import com.example.solutionchallenge.adapter.details_adapter
import java.io.Serializable

data class Nutration_data(
    val name: String,
    val img: Int,
    val details: details_adapter,
    var time: String,
    var title: String,
    var img_time:Int
) : Serializable

