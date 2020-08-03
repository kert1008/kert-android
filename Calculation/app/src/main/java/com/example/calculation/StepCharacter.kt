package com.example.calculation

class StepCharacter {
    companion object {
        private val character = mapOf(
            1 to listOf("織田信長", R.drawable.img_char01),
            2 to listOf("豊臣秀吉", R.drawable.img_char02),
            3 to listOf("徳川家康", R.drawable.img_char03)
            )

        fun getCharacter(key: Int) : List<Any>? {
            return character.get(key)
        }
    }
}