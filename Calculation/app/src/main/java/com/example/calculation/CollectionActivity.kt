package com.example.calculation

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.collection_activity.*


class CollectionActivity : AppCompatActivity() {
    private val CHARACTER_NUM = 45
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_activity)

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        val completedList = getCompletedCharacter(db)
        val allList = (1..CHARACTER_NUM).toList()

        val mInflater = LayoutInflater.from(this)
        val collection = findViewById<LinearLayout>(R.id.img_collection)

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)

        for (item in allList) {
            val view = mInflater.inflate(R.layout.img_view, collection, false);

            val img = view.findViewById<ImageView>(R.id.img)
            img.setImageResource(StepCharacter.getCharacter(item)?.get(1) as Int)

            if (item in completedList) {
                val txt = view.findViewById<TextView>(R.id.img_text)
                txt.text = StepCharacter.getCharacter(item)?.get(0).toString()
            } else {
                img.colorFilter = filter
            }
            collection.addView(view)
        }

        collection_button.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}