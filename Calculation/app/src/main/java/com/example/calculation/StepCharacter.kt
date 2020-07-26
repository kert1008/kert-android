package com.example.calculation

class StepCharacter {
    companion object {
        private val character = mapOf(
            1 to listOf("織田信長", R.drawable.img_char01),
            2 to listOf("豊臣秀吉", R.drawable.img_char02),
            3 to listOf("徳川家康", R.drawable.img_char03),
            4 to listOf("今川義元", R.drawable.img_char04),
            5 to listOf("佐竹義重", R.drawable.img_char05),
            6 to listOf("真田昌幸", R.drawable.img_char06),
            7 to listOf("村上武吉", R.drawable.img_char07),
            8 to listOf("長宗我部元親", R.drawable.img_char08),
            9 to listOf("島津義久", R.drawable.img_char09),
            10 to listOf("北条氏政", R.drawable.img_char10),
            11 to listOf("明智光秀", R.drawable.img_char11),
            12 to listOf("毛利輝元", R.drawable.img_char12),
            13 to listOf("伊達政宗", R.drawable.img_char13),
            14 to listOf("加藤清正", R.drawable.img_char14),
            15 to listOf("黒田官兵衛", R.drawable.img_char15),
            16 to listOf("黒田長政", R.drawable.img_char16),
            17 to listOf("柴田勝家", R.drawable.img_char17),
            18 to listOf("酒井忠次", R.drawable.img_char18),
            19 to listOf("上杉景勝", R.drawable.img_char19),
            20 to listOf("上杉謙信", R.drawable.img_char20),
            21 to listOf("真田幸村", R.drawable.img_char21),
            22 to listOf("石田三成", R.drawable.img_char22),
            23 to listOf("直江兼続", R.drawable.img_char23),
            24 to listOf("島津義弘", R.drawable.img_char24),
            25 to listOf("武田信玄", R.drawable.img_char25),
            26 to listOf("服部半蔵", R.drawable.img_char26),
            27 to listOf("福島正則", R.drawable.img_char27),
            28 to listOf("本多忠勝", R.drawable.img_char28)
            )

        fun getCharacter(key: Int) : List<Any>? {
            return character.get(key)
        }
    }
}