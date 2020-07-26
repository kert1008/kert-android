package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.review_activity.*

class ReviewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_activity)

        val character = intent.getIntExtra("CHARACTER", 0)
        val stepResult = intent.getStringExtra("STEP_RESULT").toString()

        Log.d("ReviewActivityTag", "Character received: $character")
        Log.d("ReviewActivityTag", "StepResult received: $stepResult")

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        val reviewList = getAllReviewExp(db)
        val reviewText = StringBuilder()
        for (item in reviewList) {
            reviewText.append(item)
            reviewText.append("\n\n")
        }
        review_text.text = reviewText.toString()

        when (stepResult) {
            "OK" -> {
                review_button.text = "降伏？逃走？"
                updateCurrentProgress(db, 2, character)
            }
            "NG" -> {
                review_button.text = "もう一度挑戦！"
            }
        }

        removeAllReviewExp(db)

        review_button.setOnClickListener {
            if (stepResult == "OK") {
                intent = Intent(this, EndActivity::class.java)
                if (judgeResult(reviewList.size)) {
                    intent.putExtra("FINAL_RESULT", "OK")
                } else {
                    intent.putExtra("FINAL_RESULT", "NG")
                }
            } else {
                intent = Intent(this, MenuActivity::class.java)
            }
            intent.putExtra("CHARACTER", character)
            Log.d("ReviewActivityTag", "Character send: $character")
            startActivity(intent)
            finish()
        }
    }

    private fun judgeResult(num: Int): Boolean {
        if (num > 3) {
            return false
        }
        if ((1..100).shuffled().first() % 2 == 0) {
            return true
        }
        return false
    }
}
