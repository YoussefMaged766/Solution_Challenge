package com.example.solutionchallenge.classes

import android.graphics.drawable.Drawable
import com.example.solutionchallenge.adapter.details_adapter
import java.io.Serializable

data class Nutration_data(
    val name: String,
    val img: Int,
    val details: details_adapter,
    var title: String,
    var img_time:Int,
    var time: String?=null,
    var txt_direction:String?=null,
    var dir:String?=null,
    var img_gif :Int?=null
) : Serializable

