package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.menu_activity.*

class MenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        step = savedInstanceState?.getInt("STEP") ?: step
        Log.d("MenuActivityTag","onCreate Step: $step")

        initMenu()

        menu_button.setOnClickListener {
            val intent = Intent(this, CalculateActivity::class.java)
            intent.putExtra("STEP", step)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        initMenu()
        Log.d("MenuActivityTag","onRestart Step: $step")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("STEP", step)
    }

    private fun initMenu() {
        step = intent.getIntExtra("STEP", step)
        Log.d("MenuActivityTag", "initMenu Step: $step")

        menu_img.setImageResource(StepCharacter.getCharacter(step)?.get(1) as Int)
        menu_text.text = (StepCharacter.getCharacter(step)?.get(0).toString())
    }
}