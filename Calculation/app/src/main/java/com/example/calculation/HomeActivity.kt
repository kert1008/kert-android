package com.example.calculation

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        Log.d("HomeActivityTag", "onCreate")

        val dbHelper = MyDatabaseHelper(this, "Calculation.db", 1)
        dbHelper.writableDatabase

        enter_button.setOnClickListener {
            val db = dbHelper.writableDatabase

            val settingCount = DatabaseUtils.queryNumEntries(db, "cal_setting").toInt()
            if (settingCount == 0) {
                addCalSetting(
                    db,
                    DEFAULT_QUESTION_NUM,
                    DEFAULT_BASE_CHANCE_NUM,
                    DEFAULT_COUNT_DOWN_MIN
                )
            }
            intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_item -> intent = Intent(this, SettingActivity::class.java)
            R.id.about_item -> intent = Intent(this, AboutActivity::class.java)
            R.id.collection_item -> intent = Intent(this, CollectionActivity::class.java)
        }
        startActivity(intent)
        return true
    }

    companion object {
        private const val DEFAULT_QUESTION_NUM = 10
        private const val DEFAULT_BASE_CHANCE_NUM = 3
        private const val DEFAULT_COUNT_DOWN_MIN = 3
    }
}