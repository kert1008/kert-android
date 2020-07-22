package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.calculate_activity.*
import java.text.SimpleDateFormat
import java.util.*

class CalculateActivity : BaseActivity() {
    private var expResult : Int = 0
    private var comLifeNum : Int = 10
    private var playLifeNum : Int = 10
    private var countDownSec : Int = 180

    private var timerText: TextView? = null
    private val dataFormat: SimpleDateFormat = SimpleDateFormat("mm:ss", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)

        step = intent.getIntExtra("STEP",step)
        Log.d("CalculateActivityTag","onCreate Step: $step")

        calculete_img.setImageResource(StepCharacter.getCharacter(step)?.get(1) as Int)

        val cntNumber: Long = (countDownSec * 1000).toLong()
        val interval: Long = 10
        val countDown = CountDown(cntNumber, interval)

        initForm(countDown)

        send_button.setOnClickListener {
            val res = result_input.text.toString()
            if (res == "") {
                Toast.makeText(this, "何も入れてないよ", Toast.LENGTH_SHORT).show()
            } else {
                when(expResult - res.toInt()) {
                    0 -> {
                        Toast.makeText(this, "敵将を討ち取った！", Toast.LENGTH_SHORT).show()
                        comLifeNum -= 1
                    }
                    else -> {
                        Toast.makeText(this, "敵にやられた...", Toast.LENGTH_SHORT).show()
                        playLifeNum -= 1
                    }
                }
                initForm(countDown)
            }
        }
    }

    private fun initForm(countDown : CountDown) {
        countDown.cancel()
        if (playLifeNum == 0 || comLifeNum == 0) {
            if (comLifeNum == 0) {
                stepResult = "OK"
            }
            val intent = Intent(this, EndActivity::class.java)
            intent.putExtra("STEP_RESULT", stepResult)
            intent.putExtra("STEP", step)
            startActivity(intent)
            finish()
        } else {
            val question = CalculationExp()
            text_exp.text = question.finalExpression
            expResult = question.finalExpResult
            result_input.text.clear()
            com_life.text = "敵の武将数： $comLifeNum"
            player_life.text = "味方の武将数： $playLifeNum"

            timerText = findViewById(R.id.player_timer);
            countDown.start()
        }
    }

    inner class CountDown(
        millisInFuture: Long,
        countDownInterval: Long
    ) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            Toast.makeText(applicationContext, "時間だ...攻撃失敗...", Toast.LENGTH_SHORT).show()
            playLifeNum -= 1
            initForm(this)
        }

        override fun onTick(millisUntilFinished: Long) {
            timerText?.setText(dataFormat.format(millisUntilFinished))
        }
    }
}