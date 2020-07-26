package com.example.calculation

import android.content.Intent
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.setting_activity.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity)

        val db = MyDatabaseHelper(this, "Calculation.db", 1).writableDatabase
        val settingRecord: List<Int> = getCalSetting(db)
        if (settingRecord.isNotEmpty()) {
            question_num.setText(settingRecord[0].toString())
            base_chance_num.setText(settingRecord[1].toString())
            count_down_min.setText(settingRecord[3].toString())
        }

        setting_button.setOnClickListener{
            if (isInputCheckPassed()) {
                updateSetting(db)
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateSetting(db: SQLiteDatabase) {
        val settingCount = DatabaseUtils.queryNumEntries(db, "cal_setting").toInt()
        if (settingCount == 0) {
            addCalSetting(db, question_num.text.toString().toInt(),
                base_chance_num.text.toString().toInt(),
                count_down_min.text.toString().toInt())
        } else {
            updateCalSetting(db, question_num.text.toString().toInt(),
                base_chance_num.text.toString().toInt(),
                count_down_min.text.toString().toInt())
        }
    }

    private fun isInputCheckPassed(): Boolean {
        val qNum = question_num.text.toString()
        val bcNum = base_chance_num.text.toString()
        val cdMin = count_down_min.text.toString()

        if (qNum.isEmpty() || qNum.toInt() < 10) {
            Toast.makeText(this, "陣の質問数を10以上の整数にしてください", Toast.LENGTH_SHORT).show()
            return false
        }
        if (bcNum.isEmpty() || bcNum.toInt() > 5) {
            Toast.makeText(this, "最初の味方数を5以下の整数にしてください", Toast.LENGTH_SHORT).show()
            return false
        }
        if (cdMin.isEmpty() || cdMin.toInt() > 3) {
            Toast.makeText(this, "回答時間を3分以下の整数にしてください", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}