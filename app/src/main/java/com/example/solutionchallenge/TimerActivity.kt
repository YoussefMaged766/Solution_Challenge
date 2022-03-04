package com.example.solutionchallenge

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.solutionchallenge.Timer.NotificationUtil
import com.example.solutionchallenge.Timer.PrefUtil
import com.example.solutionchallenge.Timer.TimerExpiredReceiver
import com.example.solutionchallenge.databinding.ActivityTimerBinding
import java.text.SimpleDateFormat
import java.util.*


class TimerActivity : AppCompatActivity() {
    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val date = Calendar.getInstance()
        var couponcreated = dateFormat.format(date.getTime())



//        binding.txtEndDate.text = couponexpires
//        binding.txtBegainDate.text = couponcreated


    }

    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped

    private var secondsRemaining: Long = 0
    private var mTimeLeftInMillis: Long = 0
    private var mEndTime: Long = 0
    private var firstTimeUsed = false
    lateinit var prefs:SharedPreferences


    lateinit var binding: ActivityTimerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
         prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        startTimer()
        binding.btnStart.isEnabled = true
        firstTimeUsed = prefs.getBoolean("firstTimeUsedKey",true);
        binding.btnStart.setOnClickListener { v ->
            startTimer()
            timerState = TimerState.Running
//            updateButtons()
             prefs = getSharedPreferences("prefs", MODE_PRIVATE)
            date.add(Calendar.DATE,91)

            var  couponexpires = dateFormat.format(date.getTime());

            val editor = prefs.edit()
            editor.putString("start" , couponcreated )
            editor.putString("end" , couponexpires)
            editor.apply()
            safe_click()
        }


        binding.txtBegainDate.text  = prefs.getString("start" , "")

        binding.txtEndDate.text = prefs.getString("end" ,"")



//        binding.btnStop.setOnClickListener { v ->
//            timer.cancel()
//            onTimerFinished()
//        }

//        var currentDate: Date = Date()
//        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
//        var current: Date = dateFormat.parse(currentDate.toString());
//        val now = Date(System.currentTimeMillis())
//        val sdf = SimpleDateFormat("dd/MM/yyyy")
//        val date = sdf.format(now)
//        binding.txtBegainDate.text = date
//        var date_after =Date(System.currentTimeMillis() +7862400000)
//        var date2 = sdf.format(date_after)
//        binding.txtEndDate.text = date2

    }



    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)

//        val now = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("dd/MM/yyyy")
//        val date = sdf.format(now)
//        binding.txtBegainDate.text = date
//        var date_after =Date(System.currentTimeMillis() +7862400000)
//        var date2 = sdf.format(date_after)
//        binding.txtEndDate.text = date2

    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running) {
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime)
        } else if (timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(this)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)

        //we don't want to change the length of the timer which is already running
        //if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
//        startTimer()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()

        binding.progressBar.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        timerState = TimerState.Running
//        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
//        var futureDate: Date = dateFormat.parse("2022-6-4");
//
//        var currentDate: Date = Date()
//        val fu: Long = currentDate.time
//        val seconds1 = fu / 1000
//        val minutes1 = seconds1 / 60
//        val hours1 = minutes1 / 60
//        val days1 = hours1 / 24 + 1
//
////        Log.e( "onTick: ",days1.toString() )
//
//        val fu2: Long = futureDate.time
//
//        val seconds2 = fu2 / 1000
//        val minutes2 = seconds2 / 60
//        val hours2 = minutes2 / 60
//        val days2 = hours2 / 24 + 1
//
//        var deff = days2 - days1
//        Log.e("onTick: ", deff.toString())
//        Log.e("startTimer: ", System.currentTimeMillis().toString())
//        Log.e("startTimer1: ", currentDate.time.toString())



        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        timer = object : CountDownTimer(mEndTime, 1000) {


            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
//                secondsRemaining = millisUntilFinished / 1000
                // Please here set your event date//YYYY-MM-DD
                mTimeLeftInMillis = millisUntilFinished
                updateCountdownUI()
                var currentDate: Date = Date()
//                if (currentDate.before(futureDate)) {
//                    var diff: Long = futureDate.getTime() - currentDate.getTime()
//                    var days: Long = diff / (24 * 60 * 60 * 1000);
//                    diff -= days * (24 * 60 * 60 * 1000);
//                    var hours: Long = diff / (60 * 60 * 1000);
//                    diff -= hours * (60 * 60 * 1000);
//                    var minutes: Long = diff / (60 * 1000);
//                    diff -= minutes * (60 * 1000);
//                    var seconds: Long = diff / 1000;
////
//                    val seconds = diff / 1000
//                    val minutes = seconds / 60
//                    val hours = minutes / 60
//                    val days = hours / 24 + 1
//                    var result = deff - days
//                    Log.e("onTick1: ", result.toString())
//                    binding.textViewProgressPercent.setText("" + String.format("%02d", days)+":"+String.format("%02d", hours)+":"+String.format("%02d", minutes)+":" +String.format("%02d", seconds));
//                    binding.textViewProgressPercent.text = (result.toString() + "/90")
//                    binding.progressBar.progress = result.toInt()


//                }

            }

        }.start()
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        binding.progressBar.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        binding.progressBar.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        var currentDate: Date = Date()

        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val date = Calendar.getInstance()
        var couponcreated = dateFormat.format(date.getTime())

        date.add(Calendar.DATE,91)

//
//        binding.txtBegainDate.text = couponcreated
      var  couponexpires = dateFormat.format(date.getTime());
//        binding.txtEndDate.text = couponexpires

        var futureDate: Date = dateFormat.parse(couponexpires);
        var diff: Long = futureDate.time - currentDate.time
        var days: Long = diff / (24 * 60 * 60 * 1000);
        diff -= days * (24 * 60 * 60 * 1000);
        var hours: Long = diff / (60 * 60 * 1000);
        diff -= hours * (60 * 60 * 1000);
        var minutes: Long = diff / (60 * 1000);
        diff -= minutes * (60 * 1000);
        var seconds: Long = diff / 1000;
        binding.progressBar.max = 90
        binding.progressBar.min =0
//        binding.textViewProgressPercent.setText("" + String.format("%02d", days)+":"+String.format("%02d", hours)+":"+String.format("%02d", minutes)+":" +String.format("%02d", seconds));
                    binding.textViewProgressPercent.text = (days.toString() + "/90")
                    binding.progressBar.progress = days.toInt()

    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                binding.btnStart.isEnabled = false
//                fab_pause.isEnabled = true
                binding.btnStop.isEnabled = true
            }
            TimerState.Stopped -> {
                binding.btnStart.isEnabled = true
//                fab_pause.isEnabled = false
                binding.btnStop.isEnabled = false
            }
            TimerState.Paused -> {
                binding.btnStart.isEnabled = true
//                fab_pause.isEnabled = false
                binding.btnStop.isEnabled = true
            }
        }
    }
    fun safe_click(){
        val editor = prefs.edit()
        editor.putBoolean("firstTimeUsedKey" , false )
        editor.apply()
        binding.btnStart.setVisibility(View.GONE);
    }





}