package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.end_activity.*

class EndActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_activity)

        var character = intent.getIntExtra("CHARACTER", 0)
        val finalResult = intent.getStringExtra("FINAL_RESULT")

        Log.d("EndActivityTag", "Character received: $character")
        Log.d("EndActivityTag", "FinalResult received: $finalResult")

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase

        if (finalResult == "OK") {
            end_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "は降伏した！"
            updateCurrentProgress(db, 1, character)
        } else {
            end_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "は逃走した！"
        }
        end_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)

        character = getCurrentCharacterNo(db)
        Log.d("EndActivityTag", "Next character: $character")

        if (character == -1) {
            end_button.text = "天下統一！"
        } else {
            end_button.text = "次の戦へ進め！"
        }

        end_button.setOnClickListener {
            if (character == -1) {
                intent = Intent(this, HomeActivity::class.java)
            } else {
                intent = Intent(this, MenuActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}