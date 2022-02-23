package com.example.solutionchallenge.classes

import android.content.Context
import android.widget.Toast

class Toast(val contest :Context , val message :String) {
    var toast = Toast.makeText(contest,message,Toast.LENGTH_SHORT).show()

}