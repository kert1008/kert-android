package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.menu_activity.*

class MenuActivity : BaseActivity() {
    var character = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        Log.d("MenuActivityTag", "onCreate")

        character = initMenu()

        menu_button.setOnClickListener {
            val intent = Intent(this, CalculateActivity::class.java)
            intent.putExtra("CHARACTER", character)
            Log.d("MenuActivityTag", "Character send: $character")
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MenuActivityTag", "onRestart")
        initMenu()
    }

    private fun initMenu(): Int {

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        character = getCurrentCharacterNo(db)
        Log.d("MenuActivityTag", "initMenu getCurrentCharacter: $character")

        if (character == -1) {
            //TODO:初期化
        }

        menu_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)
        menu_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "の陣"

        return character
    }
}