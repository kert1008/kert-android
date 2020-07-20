package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)
        val button1 = findViewById<Button>(R.id.button_1) as Button
        button1.setOnClickListener {
//            Toast.makeText(this, "OUCH! You clicked Button 1!", Toast.LENGTH_SHORT).show()
            val intent = Intent("com.example.testapp.ACTION_START")
            intent.addCategory("com.example.testapp.MY_CATEGORY_1")
            intent.putExtra("data_to_second", "DataToSecond")
//            startActivity(intent)
            startActivityForResult(intent, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_item -> Toast.makeText(this, "Item Added!", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "Item Removed!", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1 -> {
                val returnData = data!!.getStringExtra("data_back_to_first")
                Toast.makeText(this, returnData, Toast.LENGTH_SHORT).show()
            }
        }
    }
}