package com.example.calculation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val createCalSetting = "create table cal_setting (" +
            "question_num integer," +
            "base_chance_num integer," +
            "plus_chance_num integer," +
            "count_down_minute integer)"

    private val createCalProgress = "create table cal_progress (character_no integer, result integer)"
    private val createCalReview = "create table cal_review (text_exp text, exp_result integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createCalSetting)
        db.execSQL(createCalProgress)
        db.execSQL(createCalReview)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}