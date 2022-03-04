package com.example.solutionchallenge

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.solutionchallenge.Timer.NotificationUtil
import com.example.solutionchallenge.Timer.PrefUtil
import com.example.solutionchallenge.Timer.TimerExpiredReceiver
import com.example.solutionchallenge.databinding.FragmentDietBinding
import java.util.*


class DietFragment : Fragment() {



    lateinit var  binding :FragmentDietBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diet, container, false)




        return binding.root
    }


    }

