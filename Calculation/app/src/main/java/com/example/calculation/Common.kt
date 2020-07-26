package com.example.calculation

import android.database.sqlite.SQLiteDatabase

fun getCurrentCharacterNo(db: SQLiteDatabase): Int {
    val CHARACTER_NUM = 28
    var currentCharacter = findCurrentCharacter(db)

    if (currentCharacter == 0) {
        var targetList: MutableList<Int> = (1..CHARACTER_NUM).toMutableList()
        targetList.removeAll(getCompletedCharacter(db))
        if (targetList.isEmpty()) {
            return -1
        }
        currentCharacter = targetList.shuffled().first()

        if (isCharacterProgressExist(db, currentCharacter)) {
            updateCurrentProgress(db, 0, currentCharacter)
        } else {
            addCurrentCharacter(db, currentCharacter)
        }
    }

    return currentCharacter
}
