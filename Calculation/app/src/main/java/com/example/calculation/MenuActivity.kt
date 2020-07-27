package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            AlertDialog.Builder(this)
                .setTitle("天下統一した！")
                .setMessage("データを消去してやり直しますか？\nデータを消去せず自由挑戦にしますか？")
                .setPositiveButton("最初からやり直す") { dialog, which ->
                    Toast.makeText(this, "最初からやり直す", Toast.LENGTH_SHORT).show()
                    removeAllProgress(db)
                    character = getCurrentCharacterNo(db)
                    menu_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)
                    menu_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "の陣"
                }
                .setNegativeButton("自由挑戦") { dialog, which ->
                    Toast.makeText(this, "自由挑戦", Toast.LENGTH_SHORT).show()
                    character = (1..45).shuffled().first()
                    menu_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)
                    menu_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "の陣"
                }
                .show()
        } else {
            menu_img.setImageResource(StepCharacter.getCharacter(character)?.get(1) as Int)
            menu_text.text = (StepCharacter.getCharacter(character)?.get(0).toString()) + "の陣"
        }
        return character
    }
}