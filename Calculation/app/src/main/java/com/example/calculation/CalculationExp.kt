package com.example.calculation

class CalculationExp() {
    var finalExpResult = 0
    var finalExpression = ""

    private var layerNum: Int = 2

    init {
        generateExp(layerNum)
    }

    private fun generateExp(layerNum: Int) {
        var layer = listOf<Any>()

        for (i in 0 until layerNum) {
            layer = generateLayer(layer)
        }

        finalExpression = layer[1].toString()
        finalExpResult = layer[2] as Int
    }

    private fun generateLayer(exLayer: List<Any>) : List<Any> {
        val isFirstLayer: Boolean = exLayer.isEmpty()

        var exLayerExp = ""
        var exLayerResult = 0

        if (!isFirstLayer) {
            exLayerExp = exLayer[1].toString()
            exLayerResult = exLayer[2] as Int
        }

        val symbol: Int

        if (exLayerResult > 999) {
            symbol = (1..3).shuffled().first()
        } else {
            symbol = (1..4).shuffled().first()
        }

        val position = if (symbol == 4) {
            2
        } else {
            (1..2).shuffled().first()
        }

        if (!isFirstLayer) {
            val exSymbol = exLayer[0] as Int
            if (exSymbol <=2 && symbol >2) {
                exLayerExp = "($exLayerExp)"
            } else if (position == 2 && symbol%2 == 0 && exSymbol <= symbol) {
                exLayerExp = "($exLayerExp)"
            }
        }

        var expression = ""
        var expResult = 0

        var firstInt: Int
        var secondInt: Int
        var firstExp: String
        var secondExp: String

        when (symbol) {
            1 -> {
                firstInt = (10..9999).shuffled().first()
                secondInt = (10..9999).shuffled().first()
                firstExp = firstInt.toString()
                secondExp = secondInt.toString()

                if (!isFirstLayer) {
                    if (position == 1) {
                        firstInt = exLayerResult
                        firstExp = exLayerExp
                    } else {
                        secondInt = exLayerResult
                        secondExp = exLayerExp
                    }
                }

                expression = "$firstExp+$secondExp"
                expResult = firstInt + secondInt
            }

            2 -> {
                if (!isFirstLayer) {
                    if (position == 1) {
                        firstInt = exLayerResult
                        firstExp = exLayerExp
                        secondInt = (1 until firstInt).shuffled().first()
                        secondExp = secondInt.toString()
                    } else {
                        secondInt = exLayerResult
                        secondExp = exLayerExp
                        firstInt = (secondInt..9999).shuffled().first()
                        firstExp = firstInt.toString()
                    }
                } else {
                    firstInt = (10..9999).shuffled().first()
                    secondInt = (2 until firstInt).shuffled().first()
                    firstExp = firstInt.toString()
                    secondExp = secondInt.toString()
                }

                expression = "$firstExp-$secondExp"
                expResult = firstInt - secondInt
            }

            3 -> {
                firstInt = (10..99).shuffled().first()
                secondInt = (10..99).shuffled().first()
                firstExp = firstInt.toString()
                secondExp = secondInt.toString()

                if (!isFirstLayer) {
                    if (position == 1) {
                        firstInt = exLayerResult
                        firstExp = exLayerExp
                    } else {
                        secondInt = exLayerResult
                        secondExp = exLayerExp
                    }
                }

                expression = "$firstExp×$secondExp"
                expResult = firstInt * secondInt
            }

            4 -> {
                if (!isFirstLayer) {
                    secondInt = exLayerResult
                    secondExp = exLayerExp
                } else {
                    secondInt = (10..99).shuffled().first()
                    secondExp = secondInt.toString()
                }
                firstInt = (10..99).shuffled().first() * secondInt
                firstExp = firstInt.toString()

                expression = "$firstExp÷$secondExp"
                expResult = firstInt / secondInt
            }
        }

        return listOf(symbol, expression, expResult)
    }
}