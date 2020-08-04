package com.pegasus.mytimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

private val dataFormat: SimpleDateFormat = SimpleDateFormat("mm:ss.SSS", Locale.US)
private const val BROADCAST_ACTION_KEY = "MY_TIMER_UP"

class MainActivity : AppCompatActivity() {

    private lateinit var countDown: CountDown
    private lateinit var myReceiver: MyReceiver
    private var isStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_1.setOnClickListener {
            if (isStart) {
                startCountdownTimer()
                isStart = false
                button_1.text = "STOP"
            } else {
                stopCountdownTimer()
                isStart = true
                button_1.text = "START"
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(BROADCAST_ACTION_KEY)
        myReceiver = MyReceiver()
        registerReceiver(myReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }

    inner class MyReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val vibrator: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect: VibrationEffect = VibrationEffect.createOneShot(1000, DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)

            button_1.text = "START"
            isStart = true
        }
    }

    private fun startCountdownTimer() {
        val countDownSec = setting_text.text.toString().toIntOrNull()?: 0

        Log.d("MainActivity", "countDownSec=$countDownSec")
        val cntNumber: Long = (countDownSec * 1000).toLong()
        val interval: Long = 10
        countDown = CountDown(cntNumber, interval)
        countDown.start()
    }

    private fun stopCountdownTimer() {
        countDown.cancel()
    }

    inner class CountDown(millisInFuture: Long, countDownInterval: Long): CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            // Send Broadcast
            val intent = Intent(BROADCAST_ACTION_KEY)
            sendBroadcast(intent)
        }

        override fun onTick(millisUntilFinished: Long) {
            timer_text.text = dataFormat.format(millisUntilFinished)
        }
    }
}