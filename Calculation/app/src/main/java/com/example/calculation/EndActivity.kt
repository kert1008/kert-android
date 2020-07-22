package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.end_activity.*

class EndActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_activity)

        step = intent.getIntExtra("STEP",step)
        stepResult = intent.getStringExtra("STEP_RESULT").toString()

        var nextStep: Int = step

        end_img.setImageResource(StepCharacter.getCharacter(step)?.get(1) as Int)

        when(stepResult) {
            "OK" -> {
                if (step != totalStep) {
                    end_button.text = "次の戦へ進め！"
                    nextStep++
                } else {
                    end_button.text = "天下統一！"
                }
                end_text.text = (StepCharacter.getCharacter(step)?.get(0).toString()) + "を撃破した！"
            }
            "NG" -> {
                end_button.text = "もう一度挑戦！"
                end_text.text = "残念...敗北した..."
            }
        }

        end_button.setOnClickListener {
            val intent: Intent
            if (step == totalStep) {
                intent = Intent(this, EnterActivity::class.java)
            } else {
                intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("STEP", nextStep)
                Log.d("EndActivityTag","onClickListener Step: $nextStep")
            }
            startActivity(intent)
            finish()
        }
    }
}