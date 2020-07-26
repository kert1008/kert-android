package com.example.calculation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.collection_activity.*

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_activity)

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        val completedList = getCompletedCharacter(db)
//        val completedList = (1..12).toList()

        val mInflater = LayoutInflater.from(this)
        val collection = findViewById<LinearLayout>(R.id.img_collection)
        for (item in completedList) {
            var view = mInflater.inflate(R.layout.img_view, collection, false);

            var img = view.findViewById<ImageView>(R.id.img)
            img.setImageResource(StepCharacter.getCharacter(item)?.get(1) as Int)

            var txt = view.findViewById<TextView>(R.id.img_text)
            txt.text = StepCharacter.getCharacter(item)?.get(0).toString()

            collection.addView(view)
        }

        collection_button.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}