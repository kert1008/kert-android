package com.example.calculation

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

fun getCalSetting(db: SQLiteDatabase): List<Int> {
    val cursor = db.query("cal_setting", null, null, null, null, null, null)
    var resultList: MutableList<Int> = mutableListOf()
    if (cursor.moveToFirst()) {
        resultList.add(cursor.getInt(cursor.getColumnIndex("question_num")))
        resultList.add(cursor.getInt(cursor.getColumnIndex("base_chance_num")))
        resultList.add(cursor.getInt(cursor.getColumnIndex("plus_chance_num")))
        resultList.add(cursor.getInt(cursor.getColumnIndex("count_down_minute")))
    }
    cursor.close()
    return resultList
}

fun addCalSetting(db: SQLiteDatabase, qNum: Int, baseCNum: Int, cdMin: Int) {
    val settingValues = ContentValues().apply {
        put("question_num", qNum)
        put("base_chance_num", baseCNum)
        put("plus_chance_num", 0)
        put("count_down_minute", cdMin)
    }
    db.insert("cal_setting", null, settingValues)
}

fun updateCalSetting(db: SQLiteDatabase, qNum: Int, baseCNum: Int, cdMin: Int) {
    val values = ContentValues()
    values.put("question_num", qNum)
    values.put("base_chance_num", baseCNum)
    values.put("count_down_minute", cdMin)
    db.update("cal_setting", values, null, null)
}

fun updatePlusChanceNum(db: SQLiteDatabase, num: Int) {
    val values = ContentValues()
    values.put("plus_chance_num", num)
    db.update("cal_setting", values, null, null)
}

fun getCompletedCharacter(db: SQLiteDatabase): List<Int> {
    val cursor = db.query("cal_progress", null, "result = ?", arrayOf("1"), null, null, null)
    var resultList: MutableList<Int> = mutableListOf()
    if (cursor.moveToFirst()) {
        do {
            val charNo = cursor.getInt(cursor.getColumnIndex("character_no"))
            resultList.add(charNo)
        } while (cursor.moveToNext())
    }
    cursor.close()
    return resultList
}

fun findCurrentCharacter(db: SQLiteDatabase): Int {
    val cursor = db.query("cal_progress", null, "result = ?", arrayOf("0"), null, null, null)
    var resultInt = 0
    if (cursor.moveToFirst()) {
        resultInt = cursor.getInt(cursor.getColumnIndex("character_no"))
    }
    return resultInt
}

fun isCharacterProgressExist(db: SQLiteDatabase, characterNo: Int): Boolean {
    val cursor = db.query("cal_progress", null, "character_no = ?", arrayOf(characterNo.toString()), null, null, null)
    if (cursor.moveToFirst()) {
        return true
    }
    return false
}

fun addCurrentCharacter(db: SQLiteDatabase, current: Int) {
    val values = ContentValues().apply {
        put("character_no", current)
        put("result", 0)
    }
    db.insert("cal_progress", null, values)
}

fun updateCurrentProgress(db: SQLiteDatabase, result:Int, current: Int) {
    val values = ContentValues()
    values.put("result", result)
    db.update("cal_progress", values, "character_no = ?", arrayOf(current.toString()))
}

fun removeAllProgress(db: SQLiteDatabase) {
    db.delete("cal_progress", null, null)
}

fun addWrongExpForReview(db: SQLiteDatabase, textExp: String, expResult: Int) {
    val values = ContentValues().apply {
        put("text_exp", textExp)
        put("exp_result", expResult)
    }
    db.insert("cal_review", null, values)
}

fun removeAllReviewExp(db: SQLiteDatabase) {
    db.delete("cal_review", null, null)
}

fun getAllReviewExp(db: SQLiteDatabase): List<String> {
    val cursor = db.query("cal_review", null, null, null, null, null, null)
    var resultList: MutableList<String> = mutableListOf()
    if (cursor.moveToFirst()) {
        do {
            val textExp = cursor.getString(cursor.getColumnIndex("text_exp"))
            val expResult = cursor.getInt(cursor.getColumnIndex("exp_result"))
            resultList.add("$textExp=$expResult")
        } while (cursor.moveToNext())
    }
    cursor.close()
    return resultList
}