package com.example.calculation

class StepCharacter {
    companion object {
        val character = mapOf(
            1 to listOf("織田信長の陣", R.drawable.img1),
            2 to listOf("豊臣秀吉の陣", R.drawable.img2),
            3 to listOf("徳川家康の陣", R.drawable.img3)
        )

        fun getCharacter(key: Int) : List<Any>? {
            return character.get(key)
        }
    }
}