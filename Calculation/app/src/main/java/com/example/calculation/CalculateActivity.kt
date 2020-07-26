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
    private var expResult: Int = 0
    private var comLifeNum: Int = 0
    private var playLifeNum: Int = 0
    private var countDownSec: Int = 0
    private var baseLifeNum: Int = 0

    private var stepResult: String = "NG"

    private var timerText: TextView? = null
    private val dataFormat: SimpleDateFormat = SimpleDateFormat("mm:ss", Locale.US)

    private var character: Int = 0
    private var isReviewNeed = false

    private lateinit var countDown: CountDown

    private val isTest = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)

        Log.d("CalculateActivityTag", "onCreate")

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        val settingList = getCalSetting(db)
        comLifeNum = settingList[0]
        baseLifeNum = settingList[1]
        playLifeNum = settingList[1] + settingList[2]
        countDownSec = settingList[3] * 60

        character = intent.getIntExtra("CHARACTER", 0)
        Log.d("CalculateActivityTag", "Character received: $character")

        if (character == 0) {
            character = getCurrentCharacterNo(db)
        }
        calculate_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)

        val cntNumber: Long = (countDownSec * 1000).toLong()
        val interval: Long = 10
        countDown = CountDown(cntNumber, interval)

        initForm(countDown)

        send_button.setOnClickListener {
            val res = result_input.text.toString()
            if (res == "") {
                Toast.makeText(this, "何も入れてないよ", Toast.LENGTH_SHORT).show()
            } else {
                if (isTest) {
                    comLifeNum--
                } else {
                    when (expResult - res.toInt()) {
                        0 -> {
                            if (countDown.restTime > cntNumber / 2) {
                                Toast.makeText(this, "敵将は降伏して味方になった！", Toast.LENGTH_SHORT).show()
                                playLifeNum++
                            } else {
                                Toast.makeText(this, "敵将を討ち取った！", Toast.LENGTH_SHORT).show()
                            }
                            comLifeNum--
                        }
                        else -> {
                            Toast.makeText(this, "敵将にやられた...", Toast.LENGTH_SHORT).show()
                            addWrongExpForReview(db, text_exp.text.toString(), expResult)
                            isReviewNeed = true
                            playLifeNum--
                        }
                    }
                }
                initForm(countDown)
            }
        }
    }

    private fun initForm(countDown: CountDown) {
        countDown.cancel()
        if (playLifeNum == 0 || comLifeNum == 0) {
            val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase

            var num = 0
            if (comLifeNum == 0) {
                stepResult = "OK"
                num = playLifeNum - baseLifeNum
            }
            updatePlusChanceNum(db, num)

            if (isReviewNeed) {
                intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("STEP_RESULT", stepResult)
            } else {
                intent = Intent(this, EndActivity::class.java)
                intent.putExtra("FINAL_RESULT", "OK")
            }
            intent.putExtra("CHARACTER", character)
            Log.d("CalculateActivityTag", "Character send: $character")

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

    override fun onDestroy() {
        super.onDestroy()
        countDown.cancel()
    }

    inner class CountDown(
        millisInFuture: Long,
        countDownInterval: Long
    ) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var restTime: Long = 0

        override fun onFinish() {
            Toast.makeText(applicationContext, "時間だ...攻撃失敗...", Toast.LENGTH_SHORT).show()
            val db = MyDatabaseHelper(applicationContext, "Calculation.db", 1).writableDatabase
            addWrongExpForReview(db, text_exp.text.toString(), expResult)
            isReviewNeed = true
            playLifeNum -= 1
            initForm(this)
        }

        override fun onTick(millisUntilFinished: Long) {
            timerText?.text = dataFormat.format(millisUntilFinished)
            restTime = millisUntilFinished
        }
    }
}