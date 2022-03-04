package com.example.solutionchallenge.Timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.solutionchallenge.DietFragment
import com.example.solutionchallenge.TimerActivity

class TimerExpiredReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}