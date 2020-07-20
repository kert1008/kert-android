package com.example.testapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)

        Toast.makeText(this, intent.getStringExtra("data_to_second"), Toast.LENGTH_SHORT).show()

        val button2 = findViewById<Button>(R.id.button_2) as Button
        button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:10086"))
//            intent.addCategory("android.intent.category.BROWSABLE")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("data_back_to_first", "Strike back")
        setResult(RESULT_OK, intent)
        finish()
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
}